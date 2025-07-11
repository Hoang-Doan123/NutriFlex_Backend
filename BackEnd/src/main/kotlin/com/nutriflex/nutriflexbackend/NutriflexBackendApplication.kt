package com.nutriflex.nutriflexbackend

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class NutriflexBackendApplication

fun main(args: Array<String>) {
	runApplication<NutriflexBackendApplication>(*args)
}