package com.nutriflex.nutriflexbackend.service

import com.nutriflex.nutriflexbackend.model.*
import com.nutriflex.nutriflexbackend.repository.*
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Service
class MealPlanService(
    private val mealRepository: MealRepository,
    private val mealPlanRepository: MealPlanRepository
) {
    fun generateMealPlan(userId: String, goals: NutritionGoals): MealPlan {
        val allMeals = mealRepository.findAll().toMutableList()
        // 1. Filter allergies, limitations
        val filteredMeals = allMeals.filter { meal ->
            val restrictions = (goals.dietaryRestrictions ?: emptyList()) + (goals.allergies ?: emptyList())
            restrictions.none { restriction -> meal.name.contains(restriction, ignoreCase = true) }
        }.toMutableList()
        // 2. Type of food eaten recently
        val recentPlans = mealPlanRepository.findTop7ByUserIdOrderByDateDesc(userId)
        val recentMealNames = recentPlans.flatMap { it.meals }.flatMap { it.meals }.map { it.name }.toSet()
        val availableMeals = filteredMeals.filter { meal -> meal.name !in recentMealNames }.toMutableList()
        // 3. Prioritize foodPreferences (if any)
        val preferredMeals = if (!goals.foodPreferences.isNullOrEmpty()) {
            availableMeals.sortedByDescending { meal ->
                goals.foodPreferences!!.count { pref -> meal.name.contains(pref, ignoreCase = true) }
            }
        } else availableMeals
        // 4. Divide calories and macros for each meal
        val mealsPerDay = goals.mealsPerDay ?: 3
        val (mealTypes, mealTimes) = when (goals.mealPatternType) {
            "3" -> listOf("breakfast", "lunch", "dinner") to listOf("8:00 AM", "12:00 PM", "7:00 PM")
            "4a" -> listOf("breakfast", "midmorning", "lunch", "dinner") to listOf("8:00 AM", "10:30 AM", "12:00 PM", "7:00 PM")
            "4b" -> listOf("breakfast", "lunch", "afternoon", "dinner") to listOf("8:00 AM", "12:00 PM", "4:00 PM", "7:00 PM")
            "5" -> listOf("breakfast", "midmorning", "lunch", "afternoon", "dinner") to listOf("8:00 AM", "10:30 AM", "12:00 PM", "4:00 PM", "7:00 PM")
            else -> listOf("breakfast", "lunch", "dinner") to listOf("8:00 AM", "12:00 PM", "7:00 PM")
        }
        val targetCalories = goals.dailyCalories
        val proteinTarget = goals.proteinPercentage / 100.0 * targetCalories / 4.0
        val carbTarget = goals.carbohydratePercentage / 100.0 * targetCalories / 4.0
        val fatTarget = goals.fatPercentage / 100.0 * targetCalories / 9.0
        val perMealCalories = targetCalories / mealsPerDay
        val perMealProtein = proteinTarget / mealsPerDay
        val perMealCarb = carbTarget / mealsPerDay
        val perMealFat = fatTarget / mealsPerDay
        // 5. Knapsack chooses dishes for each meal
        val mealEntries = mealTypes.zip(mealTimes).map { (type, time) ->
            val mealList = knapsackMeals(
                preferredMeals,
                perMealCalories,
                perMealProtein,
                perMealCarb,
                perMealFat,
                maxMeals = 3
            )
            MealPlan.MealEntry(mealType = type, meals = mealList, time = time)
        }
        val totalCalories = mealEntries.sumOf { it.meals.sumOf { meal -> meal.calories } }
        val totalProtein = mealEntries.sumOf { it.meals.sumOf { meal -> meal.protein } }
        val totalCarbs = mealEntries.sumOf { it.meals.sumOf { meal -> meal.carbohydrate } }
        val totalFat = mealEntries.sumOf { it.meals.sumOf { meal -> meal.fat } }
        val nutritionSummary = MealPlan.NutritionSummary(
            totalCalories = totalCalories,
            targetCalories = targetCalories,
            totalProtein = totalProtein,
            totalCarbohydrates = totalCarbs,
            totalFat = totalFat,
            proteinPercentage = goals.proteinPercentage,
            carbohydratePercentage = goals.carbohydratePercentage,
            fatPercentage = goals.fatPercentage
        )
        val today = LocalDate.now().format(DateTimeFormatter.ISO_DATE)
        return MealPlan(
            userId = userId,
            date = today,
            meals = mealEntries,
            nutritionSummary = nutritionSummary
        )
    }
    // Knapsack selects maxMeals item, total calories closest to target, macro deviation least
    private fun knapsackMeals(
        meals: List<Meal>,
        targetCalories: Int,
        targetProtein: Double,
        targetCarb: Double,
        targetFat: Double,
        maxMeals: Int
    ): List<Meal> {
        // If the number of dishes is small, try all combinations.
        val candidates = meals.shuffled().take(30) // random sampling for diversity
        var bestCombo: List<Meal> = emptyList()
        var bestScore = Double.MAX_VALUE
        for (k in 1..maxMeals) {
            val combos = candidates.combinations(k)
            for (combo in combos) {
                val cal = combo.sumOf { it.calories }
                val protein = combo.sumOf { it.protein }
                val carb = combo.sumOf { it.carbohydrate }
                val fat = combo.sumOf { it.fat }
                val score =
                    Math.abs(cal - targetCalories) +
                    Math.abs(protein - targetProtein) * 5 +
                    Math.abs(carb - targetCarb) * 2 +
                    Math.abs(fat - targetFat) * 4
                if (score < bestScore) {
                    bestScore = score
                    bestCombo = combo
                }
            }
        }
        return bestCombo
    }
}
// Extension function để sinh combinations
private fun <T> List<T>.combinations(k: Int): List<List<T>> {
    if (k == 0) return listOf(emptyList())
    if (this.isEmpty()) return emptyList()
    val head = first()
    val tail = drop(1)
    val withHead = tail.combinations(k - 1).map { listOf(head) + it }
    val withoutHead = tail.combinations(k)
    return withHead + withoutHead
} 