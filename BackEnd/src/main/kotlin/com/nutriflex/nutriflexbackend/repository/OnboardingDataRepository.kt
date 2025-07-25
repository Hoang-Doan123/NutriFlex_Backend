package com.nutriflex.nutriflexbackend.repository

import com.nutriflex.nutriflexbackend.model.OnboardingData
import org.springframework.data.mongodb.repository.*
import org.springframework.stereotype.Repository

@Repository
interface OnboardingDataRepository : MongoRepository<OnboardingData, String> {
    
    @Query("{'userId': ?0}")
    fun findByUserId(userId: String): OnboardingData?
} 