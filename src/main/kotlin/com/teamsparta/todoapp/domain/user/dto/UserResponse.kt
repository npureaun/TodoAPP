package com.teamsparta.todoapp.domain.user.dto

data class UserResponse(
    val id: Long,
    val userEmail: String,
    val nickname: String,
    val role: String
)
