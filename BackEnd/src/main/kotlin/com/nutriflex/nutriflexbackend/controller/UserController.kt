package com.nutriflex.nutriflexbackend.controller

import com.nutriflex.nutriflexbackend.dto.RegisterRequest
import com.nutriflex.nutriflexbackend.model.PersonalData
import com.nutriflex.nutriflexbackend.model.User
import com.nutriflex.nutriflexbackend.repository.PersonalDataRepository
import com.nutriflex.nutriflexbackend.repository.UserRepository
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = ["*"])
class UserController(
    private val userRepository: UserRepository,
    private val personalDataRepository: PersonalDataRepository
) {
    @PostMapping("/register")
    fun registerUser(@RequestBody registerRequest: RegisterRequest): ResponseEntity<Map<String, Any>> {
        println("=== REGISTRATION REQUEST RECEIVED ===")
        println("Name: ${registerRequest.name}")
        println("Email: ${registerRequest.email}")
        println("Gender: ${registerRequest.gender}")
        println("Motivation: ${registerRequest.motivation}")
        println("Healthcare Issues: ${registerRequest.healthcareIssues}")
        println("Injuries: ${registerRequest.injuries}")
        println("Dietary Restrictions: ${registerRequest.dietaryRestrictions}")
        println("Fitness Experience: ${registerRequest.fitnessExperience}")
        
        if (userRepository.findByEmail(registerRequest.email) != null) {
            throw RuntimeException("Email already exists")
        }
        
        // Save user to user_data collection
        val user = User(
            name = registerRequest.name,
            email = registerRequest.email,
            password = registerRequest.password,
            gender = registerRequest.gender
        )
        
        val savedUser = userRepository.save(user)
        println("User saved with ID: ${savedUser.id}")
        
        // Save onboarding data to personal_data collection
        val personalData = PersonalData(
            userId = savedUser.id!!,
            motivation = registerRequest.motivation,
            healthcareIssues = registerRequest.healthcareIssues,
            injuries = registerRequest.injuries,
            dietaryRestrictions = registerRequest.dietaryRestrictions,
            fitnessExperience = registerRequest.fitnessExperience
        )
        
        println("Creating PersonalData with:")
        println("userId: ${personalData.userId}")
        println("motivation: ${personalData.motivation}")
        println("healthcareIssues: ${personalData.healthcareIssues}")
        println("injuries: ${personalData.injuries}")
        println("dietaryRestrictions: ${personalData.dietaryRestrictions}")
        println("fitnessExperience: ${personalData.fitnessExperience}")
        
        val savedPersonalData = personalDataRepository.save(personalData)
        println("PersonalData saved with ID: ${savedPersonalData.id}")
        
        // Return both user and personal data
        val response = mapOf(
            "user" to savedUser,
            "personalData" to savedPersonalData
        )
        
        println("=== REGISTRATION COMPLETED SUCCESSFULLY ===")
        return ResponseEntity.ok(response)
    }
    
    @PostMapping("/login")
    fun loginUser(@RequestBody loginRequest: Map<String, String>): ResponseEntity<User?> {
        val email = loginRequest["email"]
        val password = loginRequest["password"]
        
        if (email == null || password == null) {
            return ResponseEntity.badRequest().build()
        }
        
        val user = userRepository.findByEmail(email)
        if (user != null && user.password == password) {
            return ResponseEntity.ok(user)
        }
        
        return ResponseEntity.notFound().build()
    }
    
    @GetMapping("/{userId}/personal-data")
    fun getPersonalData(@PathVariable userId: String): ResponseEntity<PersonalData?> {
        val personalData = personalDataRepository.findByUserId(userId)
        return if (personalData != null) {
            ResponseEntity.ok(personalData)
        } else {
            ResponseEntity.notFound().build()
        }
    }
}