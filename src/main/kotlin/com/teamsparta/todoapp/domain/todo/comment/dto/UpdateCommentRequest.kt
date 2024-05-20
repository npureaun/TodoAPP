package com.teamsparta.todoapp.domain.todo.comment.dto

data class UpdateCommentRequest(
    val writer:String,
    val passWord:String,
    val comment:String,
)
