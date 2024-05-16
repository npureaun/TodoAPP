package com.teamsparta.todoapp.domain.todo.dto

import com.teamsparta.todoapp.domain.comment.dto.CommentResponse
import java.time.LocalDateTime

data class TodoResponse(
    val id: Long,
    val title:String,
    val description:String,
    val createdDate:LocalDateTime,
    val writer:String,
    val success:Boolean,
    var commentList:List<CommentResponse>,
)
