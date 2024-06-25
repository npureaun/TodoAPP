package com.teamsparta.todoapp.domain.user.controller

import com.teamsparta.todoapp.domain.user.dto.LogInUserRequest
import com.teamsparta.todoapp.domain.user.dto.LoginResponse
import com.teamsparta.todoapp.domain.user.dto.SignUpUserRequest
import com.teamsparta.todoapp.domain.user.dto.UserResponse
import com.teamsparta.todoapp.domain.user.model.UserRole
import com.teamsparta.todoapp.domain.user.service.UserService
import com.teamsparta.todoapp.infra.security.jwt.JwtUtil
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RequestMapping("/users")
@RestController
class UserController(private val userService: UserService) {
    @PostMapping("/signup")
    fun signUpUser(@RequestBody userRequest: SignUpUserRequest,
                   @RequestParam role:UserRole)//작업편의상 권한 Enum 타입 줌
    : ResponseEntity<UserResponse> {
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(userService.signUpUser(userRequest,role))
    }

    @PostMapping("/login")
    fun loginUser(@RequestBody userRequest: LogInUserRequest,
                  @RequestParam role:UserRole)//작업편의상 권한 Enum 타입 줌
    : ResponseEntity<LoginResponse> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(userService.logInUser(userRequest,role))
    }
}
