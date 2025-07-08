package com.nutriflex.nutriflexbackend.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime

@Document(collection = "workoutSessions")
data class WorkoutSession(
    @Id
    val id: String? = null,
    val userId: String,
    val type: String, // e.g. Running, Walking, Cycling
    val startTime: LocalDateTime,
    val endTime: LocalDateTime,
    val caloriesBurned: Float,
    val distance: Float,
    val steps: Int,
    val heartRateAvg: Float
) 