package com.nutriflex.nutriflexbackend.controller

import com.nutriflex.nutriflexbackend.model.Meal
import com.nutriflex.nutriflexbackend.service.MealService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

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
} 