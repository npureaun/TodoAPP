package com.teamsparta.todoapp.domain.user.dto

data class SignUpUserRequest(
    val userEmail: String,
    val userPassword: String,
    val nickname: String,
)
