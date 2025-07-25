package com.nutriflex.nutriflexbackend.controller

import com.nutriflex.nutriflexbackend.dto.calorecommend.*
import com.nutriflex.nutriflexbackend.repository.*
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/nutrition")
@CrossOrigin(origins = ["*"])
class NutritionController(
    private val userRepository: UserRepository,
    private val personalDataRepository: PersonalDataRepository
) {
    @PostMapping("/recommendation")
    fun getCalorieRecommendation(
        @RequestBody request: CalorieRecommendationRequest
    ): ResponseEntity<CalorieRecommendationResponse> {
        val userId = request.userId.toString()
        val user = userRepository.findById(userId).orElse(null)
        if (user == null) {
            return ResponseEntity.badRequest().body(CalorieRecommendationResponse(-1))
        }
        val gender = user.gender?.lowercase()
        val age = user.age
        val weight = user.weight
        val height = user.height
        if (gender == null || age == null || weight == null || height == null) {
            return ResponseEntity.badRequest().body(CalorieRecommendationResponse(-1))
        }
        // Take activityLevel from PersonalData if exist
        val personalData = personalDataRepository.findByUserId(userId)
        val activityLevel = personalData?.activityLevel ?: "moderate"
        val activityFactor = when (activityLevel) {
            "sedentary" -> 1.2
            "light" -> 1.375
            "moderate" -> 1.55
            "active" -> 1.725
            "very_active" -> 1.9
            else -> 1.55
        }
        val bmr = if (gender == "male" || gender == "nam") {
            88.362 + 13.397 * weight + 4.799 * height + (-5.677) * age
        } else {
            447.593 + 9.247 * weight + 3.098 * height + (-4.330) * age
        }
        val tdee = bmr * activityFactor

        // Adjust TDEE base on nutrition goal
        val goal = personalData?.goal
        val adjustedTdee = when (goal?.lowercase()) {
            "lose weight" -> tdee - 500
            "gain weight" -> tdee + 500
            else -> tdee // maintain weight
        }

        val recommendedCalories = (adjustedTdee + request.caloriesBurned).toInt()
        return ResponseEntity.ok(CalorieRecommendationResponse(recommendedCalories))
    }
} 