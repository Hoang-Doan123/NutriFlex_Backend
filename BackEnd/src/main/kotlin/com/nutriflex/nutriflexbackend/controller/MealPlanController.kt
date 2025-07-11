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

    @GetMapping
    fun getMealPlan(@RequestParam userId: String, @RequestParam date: String): ResponseEntity<MealPlan> {
        val plan = mealPlanRepository.findByUserIdAndDate(userId, date)
        return if (plan != null) ResponseEntity.ok(plan) else ResponseEntity.notFound().build()
    }

    @GetMapping("/{userId}/{date}")
    fun getMealPlansByPath(
        @PathVariable userId: String,
        @PathVariable date: String
    ): ResponseEntity<List<MealPlan>> {
        val plans = mealPlanRepository.findAllByUserIdAndDate(userId, date)
        println("[DEBUG] Found ${plans.size} meal plans for userId=$userId, date=$date")
        return if (plans.isNotEmpty()) ResponseEntity.ok(plans) else ResponseEntity.notFound().build()
    }
} 