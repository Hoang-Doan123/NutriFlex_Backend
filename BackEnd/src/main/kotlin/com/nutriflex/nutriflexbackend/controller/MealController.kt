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
        val allMeals = mealService.getAllMeals()
        val targetCalories = goals.dailyCalories
        val mealsPerDay = goals.mealsPerDay ?: 3
        val mealTypes = listOf("breakfast", "lunch", "dinner").take(mealsPerDay)
        val mealTimes = listOf("8:00 AM", "1:00 PM", "7:00 PM").take(mealsPerDay)
        val random = java.util.Random()
        val mealEntries = mealTypes.zip(mealTimes).map { (type, time) ->
            val mealsPerMeal = 1 + random.nextInt(5) // 1, 2 hoặc 3 món
            val mealList = allMeals.shuffled().take(mealsPerMeal)
            MealPlan.MealEntry(mealType = type, meals = mealList, time = time)
        }
        val totalCalories = mealEntries.sumOf { it.meals.sumOf { meal -> meal.calories } }
        val totalProtein = mealEntries.sumOf { it.meals.sumOf { meal -> meal.protein } }
        val totalCarbs = mealEntries.sumOf { it.meals.sumOf { meal -> meal.carbohydrate } }
        val totalFat = mealEntries.sumOf { it.meals.sumOf { meal -> meal.fat } }
        val nutritionSummary = MealPlan.NutritionSummary(
            totalCalories = totalCalories,
            targetCalories = targetCalories,
            totalProtein = totalProtein,
            totalCarbohydrates = totalCarbs,
            totalFat = totalFat
        )
        val today = java.time.LocalDate.now().format(java.time.format.DateTimeFormatter.ISO_DATE)
        val mealPlan = MealPlan(
            userId = goals.userId,
            date = today,
            meals = mealEntries,
            nutritionSummary = nutritionSummary
        )
        return ResponseEntity.ok(mealPlan)
    }
} 