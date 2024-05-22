package com.teamsparta.todoapp.domain.user.controller

import com.teamsparta.todoapp.domain.user.dto.LogInUserRequest
import com.teamsparta.todoapp.domain.user.dto.SignUpUserRequest
import com.teamsparta.todoapp.domain.user.service.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RequestMapping("/users")
@RestController
class UserController(private val userService: UserService) {
    @PostMapping("/signup")
    fun signUpUser(@RequestBody userRequest: SignUpUserRequest)
    : ResponseEntity<Unit> {
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(userService.signUpUser(userRequest))
    }

    @PostMapping("/login")
    fun loginUser(@RequestBody userRequest: LogInUserRequest)
    : ResponseEntity<Any> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(userService.logInUser(userRequest))
    }
}
