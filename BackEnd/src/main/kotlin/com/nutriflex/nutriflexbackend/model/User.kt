package com.nutriflex.nutriflexbackend.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "user_data")
data class User(
    @Id
    val id: String? = null,
    val name: String,
    val email: String,
    val password: String,
    val gender: String? = null
)
