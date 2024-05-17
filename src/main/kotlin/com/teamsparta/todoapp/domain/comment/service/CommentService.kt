package com.teamsparta.todoapp.domain.comment.service

import com.teamsparta.todoapp.domain.comment.dto.CommentResponse
import com.teamsparta.todoapp.domain.comment.dto.CreateCommentRequest
import com.teamsparta.todoapp.domain.comment.dto.DeleteCommentRequest
import com.teamsparta.todoapp.domain.comment.dto.UpdateCommentRequest

interface CommentService {
    fun getAllComment(todoId: Long): List<CommentResponse>
    fun createComment(todoId: Long,request: CreateCommentRequest): CommentResponse
    fun updateComment(commentId:Long, request: UpdateCommentRequest): CommentResponse
    fun deleteComment(commentId: Long, request: DeleteCommentRequest)
}