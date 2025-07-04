package com.nutriflex.nutriflexbackend.dto.calorecommend

data class CalorieRecommendationRequest(
    val userId: Long,
    val caloriesBurned: Int
) 