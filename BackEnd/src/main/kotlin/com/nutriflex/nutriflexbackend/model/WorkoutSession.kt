package com.nutriflex.nutriflexbackend.model

import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "workoutSessions")
data class WorkoutSession(
    @Id
    @field:JsonProperty("_id")
    val id: String? = null,
    val userId: String = "",
    val type: String = "",
    val startTime: String = "",
    val endTime: String = "",
    val caloriesBurned: Float = 0f,
    val distance: Float = 0f,
    val steps: Int = 0,
    val heartRateAvg: Float = 0f
) 