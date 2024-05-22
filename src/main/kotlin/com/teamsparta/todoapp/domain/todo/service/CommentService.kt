package com.teamsparta.todoapp.domain.todo.service

import com.teamsparta.todoapp.domain.todo.dto.comment.CommentResponse
import com.teamsparta.todoapp.domain.todo.dto.comment.CreateCommentRequest
import com.teamsparta.todoapp.domain.todo.dto.comment.DeleteCommentRequest
import com.teamsparta.todoapp.domain.todo.dto.comment.UpdateCommentRequest

interface CommentService {
    fun createComment(todoId: Long,request: CreateCommentRequest): CommentResponse
    fun updateComment(commentId:Long, request: UpdateCommentRequest): CommentResponse
    fun deleteComment(commentId: Long, request: DeleteCommentRequest)
}