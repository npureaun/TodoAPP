package com.teamsparta.todoapp.domain.todo.comment.service

import com.teamsparta.todoapp.domain.todo.comment.dto.CommentResponse
import com.teamsparta.todoapp.domain.todo.comment.dto.CreateCommentRequest
import com.teamsparta.todoapp.domain.todo.comment.dto.DeleteCommentRequest
import com.teamsparta.todoapp.domain.todo.comment.dto.UpdateCommentRequest

interface CommentService {
    fun createComment(todoId: Long,request: CreateCommentRequest): CommentResponse
    fun updateComment(commentId:Long, request: UpdateCommentRequest): CommentResponse
    fun deleteComment(commentId: Long, request: DeleteCommentRequest)
}