package com.nutriflex.nutriflexbackend.service

import com.nutriflex.nutriflexbackend.model.KcalRecord
import com.nutriflex.nutriflexbackend.repository.KcalRecordRepository
import org.springframework.stereotype.Service

@Service
class KcalService(private val repo: KcalRecordRepository) {
    // Công thức tính Kcal đốt cháy (ví dụ cho chạy bộ)
    fun calculateKcal(weight: Double, distance: Double): Double {
        // Trung bình 1kg chạy 1km đốt ~1 kcal/kg/km
        return weight * distance
    }

    fun saveKcalRecord(userId: String, distance: Double, duration: Int, weight: Double, route: String?): KcalRecord {
        val kcal = calculateKcal(weight, distance)
        val record = KcalRecord(
            userId = userId,
            distance = distance,
            duration = duration,
            weight = weight,
            kcal = kcal,
            route = route
        )
        return repo.save(record)
    }

    fun getHistory(userId: String): List<KcalRecord> = repo.findAllByUserId(userId)
} 