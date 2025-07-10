package com.nutriflex.nutriflexbackend.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.util.Date

@Document(collection = "nutrition_plan")
data class NutritionPlan(
    @Id
    val id: String? = null,
    val userId: String,
    val sessionId: String,
    val recommendedCalories: Int,
    val createdAt: Date = Date()
) 