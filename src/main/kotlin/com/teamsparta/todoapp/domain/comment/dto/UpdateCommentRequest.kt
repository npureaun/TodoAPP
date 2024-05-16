package com.teamsparta.todoapp.domain.comment.dto

data class UpdateCommentRequest(
    val writer:String,
    val passWord:String,
    val comment:String,
)
