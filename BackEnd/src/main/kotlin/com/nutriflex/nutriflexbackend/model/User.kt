package com.nutriflex.nutriflexbackend.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import com.fasterxml.jackson.annotation.JsonProperty

@Document(collection = "user_data")
data class User(
    @Id
    @get:JsonProperty("id")
    val id: String? = null,
    val name: String,
    val email: String,
    val password: String,
    val gender: String? = null,
    val age: Int? = null,
    val weight: Double? = null,
    val height: Double? = null
)
