package com.nutriflex.nutriflexbackend.controller

import com.nutriflex.nutriflexbackend.dto.*
import com.nutriflex.nutriflexbackend.model.*
import com.nutriflex.nutriflexbackend.repository.*
import org.springframework.http.*
import org.springframework.web.bind.annotation.*
import org.springframework.security.crypto.password.*

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = ["*"])
class UserController(
    private val userRepository: UserRepository,
    private val personalDataRepository: PersonalDataRepository,
    private val passwordEncoder: PasswordEncoder
) {
    @GetMapping("/health")
    fun healthCheck(): ResponseEntity<Map<String, String>> {
        return ResponseEntity.ok(mapOf("status" to "OK", "message" to "Backend is running"))
    }
    
    @PostMapping("/register")
    fun registerUser(@RequestBody registerRequest: RegisterRequest): ResponseEntity<Map<String, Any>> {
        println("=== REGISTRATION REQUEST RECEIVED ===")
        println("Name: ${registerRequest.name}")
        println("Email: ${registerRequest.email}")
        println("Gender: ${registerRequest.gender}")
        println("Age: ${registerRequest.age}")
        println("Weight: ${registerRequest.weight}")
        println("Height: ${registerRequest.height}")
        println("Goal: ${registerRequest.goal}")
        println("Motivation: ${registerRequest.motivation}")
        println("Healthcare Issues: ${registerRequest.healthcareIssues}")
        println("Injuries: ${registerRequest.injuries}")
        println("Dietary Restrictions: ${registerRequest.dietaryRestrictions}")
        println("Fitness Experience: ${registerRequest.fitnessExperience}")
        println("Activity Level: ${registerRequest.activityLevel}")
        try {
            if (userRepository.findByEmail(registerRequest.email) != null) {
                println("Error: Email already exists")
                return ResponseEntity.status(409).body(mapOf("error" to "Email already exists"))
            }
            // Save user to user_data collection
            val user = User(
                id = null,
                name = registerRequest.name,
                email = registerRequest.email,
                password = registerRequest.password,
                gender = registerRequest.gender,
                age = registerRequest.age,
                weight = registerRequest.weight,
                height = registerRequest.height
            )
            println("Saving user to database...")
            val savedUser = userRepository.save(user)
            println("User saved with ID: ${savedUser.id}")
            println("User saved to collection: user_data")
            
            // Save onboarding data to personal_data collection
            val personalData = PersonalData(
                userId = savedUser.id!!,
                motivation = registerRequest.motivation,
                healthcareIssues = registerRequest.healthcareIssues,
                injuries = registerRequest.injuries,
                dietaryRestrictions = registerRequest.dietaryRestrictions,
                fitnessExperience = registerRequest.fitnessExperience,
                goal = registerRequest.goal,
                activityLevel = registerRequest.activityLevel
            )
            println("Creating PersonalData with:")
            println("userId: ${personalData.userId}")
            println("goal: ${personalData.goal}")
            println("motivation: ${personalData.motivation}")
            println("healthcareIssues: ${personalData.healthcareIssues}")
            println("injuries: ${personalData.injuries}")
            println("dietaryRestrictions: ${personalData.dietaryRestrictions}")
            println("fitnessExperience: ${personalData.fitnessExperience}")
            println("activityLevel: ${personalData.activityLevel}")
            println("Saving personal data to database...")
            val savedPersonalData = personalDataRepository.save(personalData)
            println("PersonalData saved with ID: ${savedPersonalData.id}")
            println("PersonalData saved to collection: personal_data")
            
            // Return both user and personal data
            val response = mapOf(
                "user" to savedUser,
                "personalData" to savedPersonalData
            )
            println("=== REGISTRATION COMPLETED SUCCESSFULLY ===")
            return ResponseEntity.ok(response)
        } catch (e: Exception) {
            println("Error during registration: ${e.message}")
            e.printStackTrace()
            return ResponseEntity.status(500).body(mapOf("error" to (e.message ?: "Unknown error")))
        }
    }
    
    @PostMapping("/login")
    fun loginUser(@RequestBody loginRequest: Map<String, String>): ResponseEntity<User?> {
        val email = loginRequest["email"]
        val password = loginRequest["password"]
        println("=== LOGIN REQUEST RECEIVED ===")
        println("Email: ${email}")
        
        if (email == null || password == null) {
            println("Login failed: missing email or password")
            return ResponseEntity.badRequest().build()
        }
        
        val user = userRepository.findByEmail(email)
        val matches = when {
            user == null -> false
            user.password == password -> true
            user.password.startsWith("$2a$") || user.password.startsWith("$2b$") || user.password.startsWith("$2y$") ->
                passwordEncoder.matches(password, user.password)
            else -> false
        }

        return if (matches) {
            println("Login success for userId: ${user!!.id}")
            ResponseEntity.ok(user)
        } else {
            println("Login failed: invalid credentials for email: ${email}")
            ResponseEntity.status(401).build()
        }
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
    
    @GetMapping("/{userId}")
    fun getUserById(@PathVariable userId: String): ResponseEntity<User?> {
        val user = userRepository.findById(userId).orElse(null)
        return if (user != null) {
            ResponseEntity.ok(user)
        } else {
            ResponseEntity.notFound().build()
        }
    }
}