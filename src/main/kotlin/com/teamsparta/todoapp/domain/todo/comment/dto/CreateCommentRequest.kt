package com.teamsparta.todoapp.domain.todo.comment.dto

data class CreateCommentRequest(
    val writer:String,
    val passWord:String,
    val comment:String,
)
