package com.nutriflex.nutriflexbackend.repository

import com.nutriflex.nutriflexbackend.model.WorkoutSession
import org.springframework.data.mongodb.repository.MongoRepository

interface WorkoutSessionRepository : MongoRepository<WorkoutSession, String> {
    fun findByUserId(userId: String): List<WorkoutSession>
} 