package com.nutriflex.nutriflexbackend.dto

data class KcalRequest(
    val userId: String,
    val distance: Double, // km
    val duration: Int,    // minutes
    val weight: Double,   // kg
    val route: String?    // GeoJSON or location
) 