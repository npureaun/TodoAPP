package com.teamsparta.todoapp.domain.todo.dto

data class TodoResponse(
    val id: Long,
    val title:String,
    val description:String?,
    val dateCreated:String,
    val writer:String,
)
