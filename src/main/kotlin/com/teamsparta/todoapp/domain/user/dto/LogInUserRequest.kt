package com.teamsparta.todoapp.domain.user.dto

data class LogInUserRequest(
    val userId: String,
    val userPassword: String,
)
