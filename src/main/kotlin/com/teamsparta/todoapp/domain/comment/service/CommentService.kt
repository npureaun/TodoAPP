package com.teamsparta.todoapp.domain.comment.service

import com.teamsparta.todoapp.domain.comment.dto.CommentResponse
import com.teamsparta.todoapp.domain.comment.dto.CreateCommentRequest

interface CommentService {
    fun getAllComment(): List<CommentResponse>
    fun createComment(todoId: Long,request: CreateCommentRequest): CommentResponse
}