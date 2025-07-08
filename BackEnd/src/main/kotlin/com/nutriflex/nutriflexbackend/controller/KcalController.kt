package com.nutriflex.nutriflexbackend.controller

import com.nutriflex.nutriflexbackend.model.KcalRecord
import com.nutriflex.nutriflexbackend.service.KcalService
import com.nutriflex.nutriflexbackend.dto.KcalRequest
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/kcal")
class KcalController(private val kcalService: KcalService) {
    @PostMapping("/measure")
    fun measureAndSave(@RequestBody req: KcalRequest): KcalRecord =
        kcalService.saveKcalRecord(req.userId, req.distance, req.duration, req.weight, req.route)

    @GetMapping("/history/{userId}")
    fun getHistory(@PathVariable userId: Long): List<KcalRecord> =
        kcalService.getHistory(userId)
} 