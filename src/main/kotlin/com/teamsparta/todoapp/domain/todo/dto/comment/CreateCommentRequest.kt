package com.teamsparta.todoapp.domain.todo.dto.comment

data class CreateCommentRequest(
    val writer:String,
    val passWord:String,
    val comment:String,
)
