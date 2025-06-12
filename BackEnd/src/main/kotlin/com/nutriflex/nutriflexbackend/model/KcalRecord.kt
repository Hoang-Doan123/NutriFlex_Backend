package com.nutriflex.nutriflexbackend.model

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "kcal_records")
data class KcalRecord(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    val userId: Long,
    val distance: Double, // quãng đường (km)
    val duration: Int,    // thời gian (phút)
    val weight: Double,   // cân nặng người dùng (kg)
    val kcal: Double,     // lượng kcal đốt cháy
    val route: String?,   // dữ liệu GPS (GeoJSON hoặc chuỗi toạ độ)
    val createdAt: LocalDateTime = LocalDateTime.now()
) 