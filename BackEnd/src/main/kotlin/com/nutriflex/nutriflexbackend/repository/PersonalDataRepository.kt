package com.nutriflex.nutriflexbackend.repository

import com.nutriflex.nutriflexbackend.model.PersonalData
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface PersonalDataRepository : MongoRepository<PersonalData, String> {
    fun findByUserId(userId: String): PersonalData?
} 