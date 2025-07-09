package com.nutriflex.nutriflexbackend.controller

import com.nutriflex.nutriflexbackend.model.WorkoutSession
import com.nutriflex.nutriflexbackend.repository.WorkoutSessionRepository
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/workout-sessions")
@CrossOrigin(origins = ["*"])
class WorkoutSessionController(
    private val workoutSessionRepository: WorkoutSessionRepository
) {
    @PostMapping
    fun saveWorkoutSession(@RequestBody session: WorkoutSession): ResponseEntity<WorkoutSession> {
        val saved = workoutSessionRepository.save(session)
        return ResponseEntity.ok(saved)
    }

    @GetMapping("/user/{userId}")
    fun getSessionsByUser(@PathVariable userId: String): ResponseEntity<List<WorkoutSession>> {
        val sessions = workoutSessionRepository.findByUserId(userId)
        return ResponseEntity.ok(sessions)
    }
} 