package com.teamsparta.todoapp.domain.exception.dto

data class ErrorResponse(
    val message:String?,
    var errorCode:String?=null
)
