package com.nutriflex.nutriflexbackend.controller

import com.nutriflex.nutriflexbackend.model.*
import com.nutriflex.nutriflexbackend.service.*
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.beans.factory.annotation.Autowired
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@RestController
@RequestMapping("/api/meals")
@CrossOrigin(origins = ["*"])
class MealController(
    private val mealService: MealService
) {
    
    @GetMapping
    fun getAllMeals(): ResponseEntity<List<Meal>> {
        val meals = mealService.getAllMeals()
        return ResponseEntity.ok(meals)
    }
    
    @GetMapping("/{id}")
    fun getMealById(@PathVariable id: String): ResponseEntity<Meal> {
        val meal = mealService.getMealById(id)
        return if (meal != null) {
            ResponseEntity.ok(meal)
        } else {
            ResponseEntity.notFound().build()
        }
    }
    
    @PostMapping
    fun createMeal(@RequestBody meal: Meal): ResponseEntity<Meal> {
        val createdMeal = mealService.createMeal(meal)
        return ResponseEntity.ok(createdMeal)
    }
    
    @PutMapping("/{id}")
    fun updateMeal(@PathVariable id: String, @RequestBody meal: Meal): ResponseEntity<Meal> {
        val updatedMeal = mealService.updateMeal(id, meal)
        return if (updatedMeal != null) {
            ResponseEntity.ok(updatedMeal)
        } else {
            ResponseEntity.notFound().build()
        }
    }
    
    @DeleteMapping("/{id}")
    fun deleteMeal(@PathVariable id: String): ResponseEntity<Void> {
        val deleted = mealService.deleteMeal(id)
        return if (deleted) {
            ResponseEntity.ok().build()
        } else {
            ResponseEntity.notFound().build()
        }
    }
    
    @GetMapping("/search")
    fun searchMealsByName(@RequestParam name: String): ResponseEntity<List<Meal>> {
        val meals = mealService.searchMealsByName(name)
        return ResponseEntity.ok(meals)
    }
    
    @GetMapping("/calories")
    fun getMealsByCalorieRange(
        @RequestParam minCalories: Int,
        @RequestParam maxCalories: Int
    ): ResponseEntity<List<Meal>> {
        val meals = mealService.getMealsByCalorieRange(minCalories, maxCalories)
        return ResponseEntity.ok(meals)
    }

    @PostMapping("/generate")
    fun generateMealPlan(@RequestBody goals: NutritionGoals): ResponseEntity<MealPlan> {
        val allMeals = mealService.getAllMeals().shuffled()
        val targetCalories = goals.dailyCalories
        val mealsPerDay = goals.mealsPerDay ?: 3
        val mealTypes = listOf("breakfast", "lunch", "dinner").take(mealsPerDay)
        val mealTimes = listOf("8:00 AM", "1:00 PM", "7:00 PM").take(mealsPerDay)
        val random = java.util.Random()

        // 1. Choose the meals so that the calories are closest to targetCalories (greedy)
        val selectedMeals = mutableListOf<Meal>()
        var totalCalories = 0
        for (meal in allMeals) {
            if (totalCalories + meal.calories <= targetCalories) {
                selectedMeals.add(meal)
                totalCalories += meal.calories
            }
            if (totalCalories >= targetCalories * 0.95) break // Stop if it's enough/almost enough
        }

        // 2. If the total calories still smaller than the target, add the meal
        // which has the smallest calories value until the total calories is enough
        val minCalMeal = allMeals.minByOrNull { it.calories }
        while (totalCalories < targetCalories * 0.9 && minCalMeal != null) {
            selectedMeals.add(minCalMeal)
            totalCalories += minCalMeal.calories
        }

        // 3. If the total calories is higher than 110%, remove the meal which
        // has the highest calories value until the total calories makes sense
        while (totalCalories > targetCalories * 1.1 && selectedMeals.size > mealsPerDay) {
            val maxCalMeal = selectedMeals.maxByOrNull { it.calories }
            if (maxCalMeal != null) {
                selectedMeals.remove(maxCalMeal)
                totalCalories -= maxCalMeal.calories
            } else break
        }

        // 4. Divide the selected dishes evenly into meals
        val mealEntries = mutableListOf<MealPlan.MealEntry>()
        val chunkSize = (selectedMeals.size + mealsPerDay - 1) / mealsPerDay
        for (i in mealTypes.indices) {
            val from = i * chunkSize
            val to = minOf((i + 1) * chunkSize, selectedMeals.size)
            val mealList = if (from < to) selectedMeals.subList(from, to) else listOf()
            mealEntries.add(MealPlan.MealEntry(mealType = mealTypes[i], meals = mealList, time = mealTimes[i]))
        }

        // 5. Recalculate the total calories
        val finalCalories = mealEntries.sumOf { it.meals.sumOf { meal -> meal.calories } }
        val totalProtein = mealEntries.sumOf { it.meals.sumOf { meal -> meal.protein } }
        val totalCarbs = mealEntries.sumOf { it.meals.sumOf { meal -> meal.carbohydrate } }
        val totalFat = mealEntries.sumOf { it.meals.sumOf { meal -> meal.fat } }
        val nutritionSummary = MealPlan.NutritionSummary(
            totalCalories = finalCalories,
            targetCalories = targetCalories,
            totalProtein = totalProtein,
            totalCarbohydrates = totalCarbs,
            totalFat = totalFat
        )
        val today = LocalDate.now().format(DateTimeFormatter.ISO_DATE)
        val mealPlan = MealPlan(
            userId = goals.userId,
            date = today,
            meals = mealEntries,
            nutritionSummary = nutritionSummary
        )
        return ResponseEntity.ok(mealPlan)
    }
} 