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
    val distance: Double, // quãng đường (km)
    val duration: Int,    // thời gian (phút)
    val weight: Double,   // cân nặng người dùng (kg)
    val kcal: Double,     // lượng kcal đốt cháy
    val route: String?,   // dữ liệu GPS (GeoJSON hoặc chuỗi toạ độ)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    val createdAt: LocalDateTime = LocalDateTime.now()
) 