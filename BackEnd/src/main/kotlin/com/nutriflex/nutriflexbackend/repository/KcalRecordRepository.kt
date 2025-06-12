package com.nutriflex.nutriflexbackend.repository

import com.nutriflex.nutriflexbackend.model.KcalRecord
import org.springframework.data.jpa.repository.JpaRepository

interface KcalRecordRepository : JpaRepository<KcalRecord, Long> {
    fun findAllByUserId(userId: Long): List<KcalRecord>
} 