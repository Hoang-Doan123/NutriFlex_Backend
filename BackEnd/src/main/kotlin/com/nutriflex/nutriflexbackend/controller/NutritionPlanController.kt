package com.nutriflex.nutriflexbackend.controller

import com.nutriflex.nutriflexbackend.model.NutritionPlan
import com.nutriflex.nutriflexbackend.repository.NutritionPlanRepository
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/nutrition-plan")
@CrossOrigin(origins = ["*"])
class NutritionPlanController(
    private val nutritionPlanRepository: NutritionPlanRepository
) {
    @PostMapping
    fun saveNutritionPlan(@RequestBody plan: NutritionPlan): ResponseEntity<NutritionPlan> {
        val saved = nutritionPlanRepository.save(plan)
        return ResponseEntity.ok(saved)
    }

    @GetMapping
    fun getNutritionPlansByUserId(@RequestParam userId: String): ResponseEntity<List<NutritionPlan>> {
        val plans = nutritionPlanRepository.findAll().filter { it.userId == userId }
        return ResponseEntity.ok(plans)
    }
} 