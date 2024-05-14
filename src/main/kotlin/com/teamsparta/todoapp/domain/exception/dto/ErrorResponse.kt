package com.teamsparta.todoapp.domain.exception.dto

data class ErrorResponse(
    val message:String?,
    val errorCode:String?
)

enum class ErrorCode{
    EMAIL_NOT_EXIST,
    INVALID_PASSWORD
}