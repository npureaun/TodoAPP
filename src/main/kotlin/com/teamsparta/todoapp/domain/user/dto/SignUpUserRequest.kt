package com.teamsparta.todoapp.domain.user.dto

data class SignUpUserRequest(
    val userId: String,
    val userPassword: String,
)
