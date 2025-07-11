package com.nutriflex.nutriflexbackend.model

data class NutritionGoals(
    val userId: String,
    val dailyCalories: Int,
    val proteinPercentage: Double,
    val carbohydratePercentage: Double,
    val fatPercentage: Double,
    val dietaryRestrictions: List<String>? = null,
    val foodPreferences: List<String>? = null,
    val allergies: List<String>? = null,
    val mealPlanType: String? = null,
    val mealsPerDay: Int? = 3,
    val includeSnacks: Boolean? = false,
    val mealPatternType: String? = null
) 