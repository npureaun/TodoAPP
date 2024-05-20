package com.teamsparta.todoapp.domain.todo.comment.dto

data class CommentResponse(
    val id: Long,
    val writer:String,
    val comment:String,
)
