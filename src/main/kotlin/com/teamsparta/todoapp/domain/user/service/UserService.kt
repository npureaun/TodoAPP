package com.teamsparta.todoapp.domain.user.service

import com.teamsparta.todoapp.domain.user.dto.LogInUserRequest
import com.teamsparta.todoapp.domain.user.dto.LoginResponse
import com.teamsparta.todoapp.domain.user.dto.SignUpUserRequest
import com.teamsparta.todoapp.domain.user.dto.UserResponse
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
    fun signUpUser(request: SignUpUserRequest,role: UserRole):UserResponse {
        if (userRepository.existsByUserEmail(request.userEmail)) {
            throw IllegalStateException("Email is already in use")
        }
        val hashPassword=passwordEncoder.encode(request.userPassword)
        return userRepository.save(User.saveEntity(request,hashPassword,role))
            .toResponse()
    }

    @Transactional
    fun logInUser(request: LogInUserRequest, role: UserRole):LoginResponse {
        val user = userRepository.findByUserEmail(request.userEmail)
            ?: throw EntityNotFoundException("User Not Found")
        if (user.role.name != role.name
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