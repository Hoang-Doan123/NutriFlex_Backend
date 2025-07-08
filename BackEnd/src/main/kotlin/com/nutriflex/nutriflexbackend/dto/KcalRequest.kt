package com.nutriflex.nutriflexbackend.dto

data class KcalRequest(
    val userId: Long,
    val distance: Double, // km
    val duration: Int,    // phút
    val weight: Double,   // kg
    val route: String?    // GeoJSON hoặc chuỗi toạ độ
) 