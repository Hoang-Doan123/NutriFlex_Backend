package com.nutriflex.nutriflexbackend.service

import com.nutriflex.nutriflexbackend.model.KcalRecord
import com.nutriflex.nutriflexbackend.repository.KcalRecordRepository
import org.springframework.stereotype.Service

@Service
class KcalService(private val repo: KcalRecordRepository) {
    // Formula for calculating Kcal burned (example for jogging)
    fun calculateKcal(weight: Double, distance: Double): Double {
        // On average, 1kg running 1km burns ~1 kcal/kg/km
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