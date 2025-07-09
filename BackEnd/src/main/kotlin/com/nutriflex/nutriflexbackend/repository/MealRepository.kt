package com.nutriflex.nutriflexbackend.repository

import com.nutriflex.nutriflexbackend.model.Meal
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface MealRepository : MongoRepository<Meal, String> {
    
    fun findByNameContainingIgnoreCase(name: String): List<Meal>
    
    fun findByCaloriesBetween(minCalories: Int, maxCalories: Int): List<Meal>
} 