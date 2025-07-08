package com.nutriflex.nutriflexbackend.dto

data class RegisterRequest(
    val name: String,
    val email: String,
    val password: String,
    val gender: String? = null,
    val age: Int? = null,
    val weight: Double? = null,
    val height: Double? = null,
    val motivation: String? = null,
    val healthcareIssues: List<String>? = null,
    val injuries: List<String>? = null,
    val dietaryRestrictions: List<String>? = null,
    val fitnessExperience: String? = null,
    val goal: String? = null
) 