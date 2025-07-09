package com.nutriflex.nutriflexbackend.controller

import com.nutriflex.nutriflexbackend.model.*
import com.nutriflex.nutriflexbackend.service.*
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/meal-plans")
@CrossOrigin(origins = ["*"])
class MealPlanController(
    private val mealPlanService: MealPlanService
) {
    @PostMapping("/generate")
    fun generateMealPlan(@RequestParam userId: String, @RequestBody goals: NutritionGoals): ResponseEntity<MealPlan> {
        val mealPlan = mealPlanService.generateMealPlan(userId, goals)
        return ResponseEntity.ok(mealPlan)
    }
} 