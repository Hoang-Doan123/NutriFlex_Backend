package com.nutriflex.nutriflexbackend.controller

import com.nutriflex.nutriflexbackend.model.OnboardingData
import com.nutriflex.nutriflexbackend.service.OnboardingService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/onboarding")
@CrossOrigin(origins = ["*"])
class OnboardingController(
    private val onboardingService: OnboardingService
) {
    
    @PostMapping("/save")
    fun saveOnboardingData(
        @RequestParam userId: String,
        @RequestBody onboardingData: Map<String, Any>
    ): ResponseEntity<OnboardingData> {
        val savedData = onboardingService.saveOnboardingData(userId, onboardingData)
        return ResponseEntity.ok(savedData)
    }
    
    @GetMapping("/data")
    fun getOnboardingData(@RequestParam userId: String): ResponseEntity<OnboardingData?> {
        val data = onboardingService.getOnboardingData(userId)
        return ResponseEntity.ok(data)
    }
} 