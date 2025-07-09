package com.nutriflex.nutriflexbackend.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "meal_plans")
data class MealPlan(
    @Id
    val id: String? = null,
    val userId: String,
    val date: String,
    val meals: List<MealEntry>,
    val nutritionSummary: NutritionSummary
) {
    data class MealEntry(
        val mealType: String, // breakfast, lunch, dinner
        val meal: Meal,
        val time: String // e.g. "8:00 AM"
    )
    data class NutritionSummary(
        val totalCalories: Int,
        val targetCalories: Int,
        val totalProtein: Double,
        val totalCarbohydrates: Double,
        val totalFat: Double
    )
} 