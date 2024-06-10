package com.teamsparta.todoapp.domain.user.service

import org.springframework.context.annotation.Bean
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.provisioning.InMemoryUserDetailsManager

class CustomUserDetailsService : UserDetailsService {

    //@Authenticationprincipal 도입 시도
    override fun loadUserByUsername(username: String): UserDetails {
        return if (username == "user") {
            User.withUsername("user")
                .password("{noop}password")
                .roles("USER")
                .build()
        } else {
            throw UsernameNotFoundException("User not found with username: $username")
        }
    }

    @Bean
    fun inMemoryUserDetailsManager(): InMemoryUserDetailsManager {
        val user = User.withUsername("user")
            .password("{noop}password")
            .roles("USER")
            .build()
        return InMemoryUserDetailsManager(user)
    }
}