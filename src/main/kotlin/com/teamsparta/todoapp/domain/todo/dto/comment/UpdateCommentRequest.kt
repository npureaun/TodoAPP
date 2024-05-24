package com.teamsparta.todoapp.domain.todo.dto.comment

data class UpdateCommentRequest(
    val writer:String,
    val passWord:String,
    val comment:String,
    val token:String?
)
