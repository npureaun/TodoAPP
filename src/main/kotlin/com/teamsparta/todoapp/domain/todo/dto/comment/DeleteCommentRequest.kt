package com.teamsparta.todoapp.domain.todo.dto.comment

data class DeleteCommentRequest(
    val writer:String,
    val passWord:String,
    val token:String?
)
