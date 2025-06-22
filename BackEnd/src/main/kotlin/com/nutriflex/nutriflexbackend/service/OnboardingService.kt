package com.nutriflex.nutriflexbackend.service

import com.nutriflex.nutriflexbackend.model.OnboardingData
import com.nutriflex.nutriflexbackend.repository.OnboardingDataRepository
import org.springframework.stereotype.Service

@Service
class OnboardingService(
    private val onboardingDataRepository: OnboardingDataRepository
) {
    
    fun saveOnboardingData(userId: String, onboardingData: Map<String, Any>): OnboardingData {
        val existingData = onboardingDataRepository.findByUserId(userId)
        
        val data = OnboardingData(
            id = existingData?.id,
            userId = userId,
            gender = onboardingData["gender"] as? String,
            motivation = onboardingData["motivation"] as? List<String> ?: emptyList(),
            healthIssues = onboardingData["healthIssues"] as? List<String> ?: emptyList(),
            fitnessExperience = onboardingData["fitnessExperience"] as? String
        )
        
        return onboardingDataRepository.save(data)
    }
    
    fun getOnboardingData(userId: String): OnboardingData? {
        return onboardingDataRepository.findByUserId(userId)
    }
} 