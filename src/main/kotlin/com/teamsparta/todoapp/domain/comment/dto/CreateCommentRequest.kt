package com.teamsparta.todoapp.domain.comment.dto

data class CreateCommentRequest(
    val writer:String,
    val passWord:String,
    val comment:String,
)
