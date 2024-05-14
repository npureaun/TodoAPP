package com.teamsparta.todoapp.domain.comment.dto

data class CommentResponse(
    val id: Long,
    val writer:String,
    val comment:String,
)
