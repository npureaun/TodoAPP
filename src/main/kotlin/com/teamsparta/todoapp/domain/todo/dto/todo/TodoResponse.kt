package com.teamsparta.todoapp.domain.todo.dto.todo

import com.teamsparta.todoapp.domain.todo.dto.comment.CommentResponse
import java.time.LocalDateTime

data class TodoResponse(
    val id: Long,
    val title:String,
    val description:String,
    val created:LocalDateTime,
    val nickname:String,
    val success:Boolean,
    var commentList:List<CommentResponse>,
)
