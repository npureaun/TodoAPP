package com.teamsparta.todoapp.domain.user.service

import com.teamsparta.todoapp.domain.user.dto.LogInUserRequest
import com.teamsparta.todoapp.domain.user.dto.SignUpUserRequest
import com.teamsparta.todoapp.domain.user.model.User
import com.teamsparta.todoapp.domain.user.repository.UserRepository
import com.teamsparta.todoapp.domain.security.encode.BCHash
import com.teamsparta.todoapp.domain.security.jwt.JwtUtil
import jakarta.persistence.EntityNotFoundException
import jakarta.transaction.Transactional
import org.hibernate.service.spi.ServiceException
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.stereotype.Service
import javax.naming.AuthenticationException

@Service
class UserServiceImpl(private val userRepository: UserRepository): UserService {

    @Transactional
    override fun signUpUser(request: SignUpUserRequest) {
        try {
            userRepository.save(
                User(
                    userId = request.userId,
                    userPassword = BCHash.hashPassword(request.userPassword)
                )
            )
        } catch (e: DataIntegrityViolationException) {
            throw ServiceException("Data Duplication")
        }
    }

    @Transactional
    override fun logInUser(request: LogInUserRequest):String {
        val dbPassword = userRepository.findByUserId(request.userId)
            ?: throw EntityNotFoundException("User Not Found")
        if (BCHash.verifyPassword(request.userPassword, dbPassword.userPassword))
        {
            return JwtUtil.generateAccessToken("userId", request.userId)
        }
        else throw AuthenticationException("User Password Not Match")
    }

    @Transactional
    override fun getUserInfo(token:String): String{
        return JwtUtil.getUserIdFromToken(token)!!
    }
}