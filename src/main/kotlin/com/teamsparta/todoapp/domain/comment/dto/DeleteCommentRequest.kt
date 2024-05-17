package com.teamsparta.todoapp.domain.comment.dto

data class DeleteCommentRequest(
    val writer:String,
    val passWord:String,
)
