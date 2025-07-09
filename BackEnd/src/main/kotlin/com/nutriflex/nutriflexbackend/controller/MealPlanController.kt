package com.nutriflex.nutriflexbackend.controller

import com.nutriflex.nutriflexbackend.model.*
import com.nutriflex.nutriflexbackend.service.*
import com.nutriflex.nutriflexbackend.repository.*
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/meal-plans")
@CrossOrigin(origins = ["*"])
class MealPlanController(
    private val mealPlanService: MealPlanService,
    private val mealPlanRepository: MealPlanRepository
) {
    @PostMapping("/generate")
    fun generateMealPlan(@RequestParam userId: String, @RequestBody goals: NutritionGoals): ResponseEntity<MealPlan> {
        val mealPlan = mealPlanService.generateMealPlan(userId, goals)
        return ResponseEntity.ok(mealPlan)
    }

    @PostMapping
    fun saveMealPlan(@RequestBody mealPlan: MealPlan): ResponseEntity<MealPlan> {
        val saved = mealPlanRepository.save(mealPlan)
        return ResponseEntity.ok(saved)
    }
} 