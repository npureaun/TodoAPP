package com.teamsparta.todoapp.domain.todo.dto

data class CreateTodoRequest(
    val title:String,
    val writer:String,
    val description:String,
)
