package com.nutriflex.nutriflexbackend.controller

import com.nutriflex.nutriflexbackend.model.KcalRecord
import com.nutriflex.nutriflexbackend.service.KcalService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/kcal")
class KcalController(private val kcalService: KcalService) {
    data class KcalRequest(
        val userId: Long,
        val distance: Double, // km
        val duration: Int,    // phút
        val weight: Double,   // kg
        val route: String?    // GeoJSON hoặc chuỗi toạ độ
    )

    @PostMapping("/measure")
    fun measureAndSave(@RequestBody req: KcalRequest): KcalRecord =
        kcalService.saveKcalRecord(req.userId, req.distance, req.duration, req.weight, req.route)

    @GetMapping("/history/{userId}")
    fun getHistory(@PathVariable userId: Long): List<KcalRecord> =
        kcalService.getHistory(userId)
} 