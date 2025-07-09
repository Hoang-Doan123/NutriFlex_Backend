package com.nutriflex.nutriflexbackend.controller

import com.nutriflex.nutriflexbackend.dto.calorecommend.CalorieRecommendationRequest
import com.nutriflex.nutriflexbackend.dto.calorecommend.CalorieRecommendationResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/nutrition")
@CrossOrigin(origins = ["*"])
class NutritionController {
    @PostMapping("/recommendation")
    fun getCalorieRecommendation(
        @RequestBody request: CalorieRecommendationRequest
    ): ResponseEntity<CalorieRecommendationResponse> {
        val baseCalories = 200 // Mock BMR/TDEE
        val recommendedCalories = baseCalories + request.caloriesBurned
        return ResponseEntity.ok(CalorieRecommendationResponse(recommendedCalories))
    }
} 