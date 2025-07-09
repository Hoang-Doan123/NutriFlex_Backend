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
        val meals: List<Meal>, // <-- List thay vì 1 món
        val time: String, // e.g. "8:00 AM"
        val portionSize: Int? = null, // Số lượng phần ăn, đồng bộ với frontend
        val notes: String? = null // Ghi chú, đồng bộ với frontend
    )
    data class NutritionSummary(
        val totalCalories: Int,
        val targetCalories: Int,
        val totalProtein: Double,
        val totalCarbohydrates: Double,
        val totalFat: Double,
        val proteinPercentage: Double? = null,
        val carbohydratePercentage: Double? = null,
        val fatPercentage: Double? = null
    )
} 