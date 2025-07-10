package com.nutriflex.nutriflexbackend.repository

import com.nutriflex.nutriflexbackend.model.MealPlan
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface MealPlanRepository : MongoRepository<MealPlan, String> {
    fun findByUserIdAndDate(userId: String, date: String): MealPlan?
    fun findTop7ByUserIdOrderByDateDesc(userId: String): List<MealPlan>
} 