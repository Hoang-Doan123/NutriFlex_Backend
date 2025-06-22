package com.nutriflex.nutriflexbackend.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "onboarding_data")
data class OnboardingData(
    @Id
    val id: String? = null,
    val userId: String,
    val gender: String? = null,
    val motivation: List<String> = emptyList(),
    val healthIssues: List<String> = emptyList(),
    val fitnessExperience: String? = null,
    val createdAt: java.time.LocalDateTime = java.time.LocalDateTime.now()
) 