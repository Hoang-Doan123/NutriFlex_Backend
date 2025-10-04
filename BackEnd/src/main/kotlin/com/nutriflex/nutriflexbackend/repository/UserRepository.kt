package com.nutriflex.nutriflexbackend.repository

import com.nutriflex.nutriflexbackend.model.User
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : MongoRepository<User, String> {
    fun findByEmail(email: String): User?
    fun findByName(name: String): User?
}