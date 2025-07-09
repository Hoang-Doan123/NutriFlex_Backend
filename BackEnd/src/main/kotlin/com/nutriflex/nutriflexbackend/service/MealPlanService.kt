package com.nutriflex.nutriflexbackend.service

import com.nutriflex.nutriflexbackend.model.Meal
import com.nutriflex.nutriflexbackend.model.MealPlan
import com.nutriflex.nutriflexbackend.model.NutritionGoals
import com.nutriflex.nutriflexbackend.repository.MealRepository
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Service
class MealPlanService(
    private val mealRepository: MealRepository
) {
    fun generateMealPlan(userId: String, goals: NutritionGoals): MealPlan {
        val allMeals = mealRepository.findAll()
        val targetCalories = goals.dailyCalories
        val mealsPerDay = goals.mealsPerDay ?: 3
        val mealTypes = listOf("breakfast", "lunch", "dinner").take(mealsPerDay)
        val mealTimes = listOf("8:00 AM", "1:00 PM", "7:00 PM").take(mealsPerDay)
        val selectedMeals = allMeals.shuffled().take(mealsPerDay)
        val mealEntries = mealTypes.zip(selectedMeals).zip(mealTimes) { (type, meal), time ->
            MealPlan.MealEntry(mealType = type, meal = meal, time = time)
        }
        val totalCalories = mealEntries.sumOf { it.meal.calories }
        val totalProtein = mealEntries.sumOf { it.meal.protein }
        val totalCarbs = mealEntries.sumOf { it.meal.carbohydrate }
        val totalFat = mealEntries.sumOf { it.meal.fat }
        val nutritionSummary = MealPlan.NutritionSummary(
            totalCalories = totalCalories,
            targetCalories = targetCalories,
            totalProtein = totalProtein,
            totalCarbohydrates = totalCarbs,
            totalFat = totalFat
        )
        val today = LocalDate.now().format(DateTimeFormatter.ISO_DATE)
        return MealPlan(
            userId = userId,
            date = today,
            meals = mealEntries,
            nutritionSummary = nutritionSummary
        )
    }
} 