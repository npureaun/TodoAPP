package com.teamsparta.todoapp.domain.user.service

import com.teamsparta.todoapp.domain.user.dto.LogInUserRequest
import com.teamsparta.todoapp.domain.user.dto.SignUpUserRequest

interface UserService {
    fun signUpUser(request: SignUpUserRequest)
    fun logInUser(request: LogInUserRequest):String
}