package com.nutriflex.nutriflexbackend.service

import com.nutriflex.nutriflexbackend.model.Meal
import com.nutriflex.nutriflexbackend.repository.MealRepository
import org.springframework.stereotype.Service

@Service
class MealService(
    private val mealRepository: MealRepository
) {
    
    fun getAllMeals(): List<Meal> {
        return mealRepository.findAll()
    }
    
    fun getMealById(id: String): Meal? {
        return mealRepository.findById(id).orElse(null)
    }
    
    fun createMeal(meal: Meal): Meal {
        return mealRepository.save(meal)
    }
    
    fun updateMeal(id: String, meal: Meal): Meal? {
        val existingMeal = mealRepository.findById(id).orElse(null)
        if (existingMeal != null) {
            val updatedMeal = meal.copy(id = id)
            return mealRepository.save(updatedMeal)
        }
        return null
    }
    
    fun deleteMeal(id: String): Boolean {
        val meal = mealRepository.findById(id).orElse(null)
        if (meal != null) {
            mealRepository.deleteById(id)
            return true
        }
        return false
    }
    
    fun searchMealsByName(name: String): List<Meal> {
        return mealRepository.findByNameContainingIgnoreCase(name)
    }
    
    fun getMealsByCalorieRange(minCalories: Int, maxCalories: Int): List<Meal> {
        return mealRepository.findByCaloriesBetween(minCalories, maxCalories)
    }
} 