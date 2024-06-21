package com.teamsparta.todoapp.domain.user.service

import com.teamsparta.todoapp.domain.user.dto.LogInUserRequest
import com.teamsparta.todoapp.domain.user.dto.LoginResponse
import com.teamsparta.todoapp.domain.user.dto.SignUpUserRequest
import com.teamsparta.todoapp.domain.user.dto.UserResponse
import com.teamsparta.todoapp.domain.user.model.Profile
import com.teamsparta.todoapp.domain.user.model.User
import com.teamsparta.todoapp.domain.user.model.UserRole
import com.teamsparta.todoapp.domain.user.model.toResponse
import com.teamsparta.todoapp.domain.user.repository.UserRepository
import com.teamsparta.todoapp.infra.security.jwt.JwtUtil
import jakarta.persistence.EntityNotFoundException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import javax.naming.AuthenticationException

@Service
class UserService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val jwtUtil: JwtUtil,
){

    @Transactional
    fun signUpUser(request: SignUpUserRequest):UserResponse {
        if (userRepository.existsByUserEmail(request.userEmail)) {
            throw IllegalStateException("Email is already in use")
        }

        return userRepository.save(
            User(
                userEmail = request.userEmail,
                userPassword = passwordEncoder.encode(request.userPassword),
                profile = Profile(nickname = request.nickname),
                role = when (request.role) {
                    UserRole.STANDARD.name -> UserRole.STANDARD
                    UserRole.DEVELOP.name -> UserRole.DEVELOP
                    else -> throw IllegalArgumentException("Invalid role")
                }
            )
        ).toResponse()
    }

    @Transactional
    fun logInUser(request: LogInUserRequest):LoginResponse {
        val user = userRepository.findByUserEmail(request.userEmail)
            ?: throw EntityNotFoundException("User Not Found")
        if (user.role.name != request.role
            || !passwordEncoder.matches(request.userPassword, user.userPassword))
            throw AuthenticationException("User Info Not Match")
        return LoginResponse(
            accessToken = jwtUtil.generateAccessToken(
                subject = user.id.toString(),
                role = user.role.name
            ))
    }

    fun getUserInfo():UserResponse{
        return SecurityContextHolder
            .getContext().authentication.principal.toString()
            .let { """id=([^,]+)""".toRegex().find(it) }
            .let { it?.groups?.get(1)?.value
                ?: throw EntityNotFoundException("User Pk not found in Token") }
            .let { userRepository.findByIdOrNull(it.toLong())?.toResponse()
                ?:throw EntityNotFoundException("User Not Found")}
    }
}