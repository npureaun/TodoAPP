package com.teamsparta.todoapp.domain.user.dto

data class LogInUserRequest(
    val userEmail: String,
    val userPassword: String,
    val role:String
)
