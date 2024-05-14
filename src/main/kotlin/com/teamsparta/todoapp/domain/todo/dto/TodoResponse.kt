package com.teamsparta.todoapp.domain.todo.dto

import java.time.LocalDateTime

data class TodoResponse(
    val id: Long,
    val title:String,
    val description:String,
    val createdDate:LocalDateTime,
    val writer:String,
)
