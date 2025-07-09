package com.nutriflex.nutriflexbackend.repository

import com.nutriflex.nutriflexbackend.model.Questionnaire
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface QuestionnaireRepository : MongoRepository<Questionnaire, String> {
    fun findByUserId(userId: String): List<Questionnaire>
} 