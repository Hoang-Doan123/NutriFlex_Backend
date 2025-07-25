package com.nutriflex.nutriflexbackend.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "personal_data")
data class PersonalData(
    @Id
    val id: String? = null,
    val userId: String,
    val motivation: String? = null,
    val healthcareIssues: List<String>? = null,
    val injuries: List<String>? = null,
    val dietaryRestrictions: List<String>? = null,
    val fitnessExperience: String? = null,
    val goal: String? = null,
    val activityLevel: String? = null // "sedentary", "light", "moderate", "active", "very_active"
) 