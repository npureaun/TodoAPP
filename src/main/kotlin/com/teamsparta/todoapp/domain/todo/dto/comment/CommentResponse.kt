package com.teamsparta.todoapp.domain.todo.dto.comment

data class CommentResponse(
    val id: Long,
    val writer:String,
    val comment:String,
)
