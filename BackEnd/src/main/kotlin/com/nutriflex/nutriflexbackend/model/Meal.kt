package com.nutriflex.nutriflexbackend.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "meals")
data class Meal(
    @Id
    val id: String? = null,
    val name: String,
    val calories: Int,
    val protein: Double,
    val carbohydrate: Double,
    val fat: Double
) 