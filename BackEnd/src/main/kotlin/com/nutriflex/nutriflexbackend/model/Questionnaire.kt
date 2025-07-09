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
    val workoutType: String
) 