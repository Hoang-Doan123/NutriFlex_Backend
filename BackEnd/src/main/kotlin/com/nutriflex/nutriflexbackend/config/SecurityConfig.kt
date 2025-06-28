package com.nutriflex.nutriflexbackend.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.web.SecurityFilterChain

@Configuration
@EnableWebSecurity
class SecurityConfig {

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .csrf { it.disable() }
            .authorizeHttpRequests { authz ->
                authz
//                    .requestMatchers("/api/users/health").permitAll()
                    .requestMatchers("/api/users/register").permitAll()
                    .requestMatchers("/api/users/login").permitAll()
//                    .requestMatchers("/api/fatsecret/**").permitAll()
//                    .requestMatchers("/actuator/**").permitAll()
                    .anyRequest().authenticated()
            }
            .oauth2Client { }
        
        return http.build()
    }
} 