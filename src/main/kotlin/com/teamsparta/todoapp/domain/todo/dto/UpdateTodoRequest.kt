package com.teamsparta.todoapp.domain.todo.dto

data class UpdateTodoRequest(
    val title:String,
    val writer:String,
    val description:String,
)