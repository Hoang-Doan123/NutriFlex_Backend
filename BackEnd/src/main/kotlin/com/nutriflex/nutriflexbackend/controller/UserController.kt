package com.nutriflex.nutriflexbackend.controller

import com.nutriflex.nutriflexbackend.model.User
import com.nutriflex.nutriflexbackend.repository.UserRepository
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/users")
class UserController(
    private val userRepository: UserRepository
) {
    @PostMapping("/register")
    fun registerUser(@RequestBody user: User): User {
        if (userRepository.findByEmail(user.email) != null) {
            throw RuntimeException("Email already exists")
        }
        return userRepository.save(user)
    }
}