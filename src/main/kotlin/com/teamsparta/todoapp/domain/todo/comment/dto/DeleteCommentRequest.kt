package com.teamsparta.todoapp.domain.todo.comment.dto

data class DeleteCommentRequest(
    val writer:String,
    val passWord:String,
)
