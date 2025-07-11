package com.nutriflex.nutriflexbackend.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.web.bind.annotation.CrossOrigin

@CrossOrigin(origins = ["*"])
@Document(collection = "questionnaires")
data class Questionnaire(
    @Id
    val id: String? = null,
    val userId: String,
    val workoutType: String? = null,
    val fitnessExperience: String? = null,
    val injuries: List<String>? = null,
    val healthcareIssues: List<String>? = null,
    val dietaryRestrictions: List<String>? = null,
    val goal: String? = null,
    val motivation: String? = null
) 