package com.nutriflex.nutriflexbackend.repository

import com.nutriflex.nutriflexbackend.model.NutritionPlan
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface NutritionPlanRepository : MongoRepository<NutritionPlan, String> 