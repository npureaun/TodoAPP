package com.teamsparta.todoapp.domain.user.service

import com.teamsparta.todoapp.domain.exception.ModelNotFoundException
import com.teamsparta.todoapp.domain.user.dto.LogInUserRequest
import com.teamsparta.todoapp.domain.user.dto.SignUpUserRequest
import com.teamsparta.todoapp.domain.user.model.User
import com.teamsparta.todoapp.domain.user.repository.UserRepository
import jakarta.persistence.EntityNotFoundException
import jakarta.transaction.Transactional
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class UserServiceImpl(private val userRepository: UserRepository): UserService {

    @Transactional
    override fun signUpUser(request: SignUpUserRequest) {
        userRepository.save(
            User(
                userId = request.userId,
                userPassword = request.userPassword
            )
        )
    }

    @Transactional
    override fun logInUser(request: LogInUserRequest) {
        val userInfo=userRepository.findByUserId(request.userId)
            ?:throw EntityNotFoundException("UnMatching userId")
        if(userInfo.userPassword==request.userPassword){
            println(userInfo.id)
            println(userInfo.userId)
            println(userInfo.userPassword)
        }
        else throw EntityNotFoundException("UnMatching userPassword")
    }
}