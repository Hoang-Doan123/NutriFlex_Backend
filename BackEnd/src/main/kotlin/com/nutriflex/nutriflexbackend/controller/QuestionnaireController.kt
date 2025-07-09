package com.nutriflex.nutriflexbackend.controller

import com.nutriflex.nutriflexbackend.model.Questionnaire
import com.nutriflex.nutriflexbackend.repository.QuestionnaireRepository
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/questionnaires")
class QuestionnaireController(
    private val questionnaireRepository: QuestionnaireRepository
) {
    @PostMapping
    fun saveQuestionnaire(@RequestBody request: Questionnaire): ResponseEntity<Questionnaire> {
        val saved = questionnaireRepository.save(request)
        return ResponseEntity.ok(saved)
    }

    @GetMapping("/{userId}")
    fun getByUserId(@PathVariable userId: String): ResponseEntity<List<Questionnaire>> {
        val list = questionnaireRepository.findByUserId(userId)
        return ResponseEntity.ok(list)
    }
} 