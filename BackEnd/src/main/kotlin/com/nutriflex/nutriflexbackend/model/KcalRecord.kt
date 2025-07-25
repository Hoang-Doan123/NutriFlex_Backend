package com.nutriflex.nutriflexbackend.model

import com.fasterxml.jackson.annotation.JsonFormat
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "kcal_records")
data class KcalRecord(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    val userId: String,
    val distance: Double, // distance (km)
    val duration: Int,    // time (minutes)
    val weight: Double,   // user weight (kg)
    val kcal: Double,     // calories burned
    val route: String?,   // GPS data (GeoJSON or location)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    val createdAt: LocalDateTime = LocalDateTime.now()
) 