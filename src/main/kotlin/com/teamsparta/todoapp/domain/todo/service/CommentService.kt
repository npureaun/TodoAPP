package com.teamsparta.todoapp.domain.todo.service

import com.teamsparta.todoapp.domain.todo.dto.comment.CommentResponse
import com.teamsparta.todoapp.domain.todo.dto.comment.CreateCommentRequest
import com.teamsparta.todoapp.domain.todo.dto.comment.UpdateCommentRequest
import com.teamsparta.todoapp.domain.todo.model.Comment
import com.teamsparta.todoapp.domain.todo.model.toResponse
import com.teamsparta.todoapp.domain.todo.repository.comment.CommentRepository
import com.teamsparta.todoapp.domain.exception.ModelNotFoundException
import com.teamsparta.todoapp.domain.todo.repository.todo.TodoRepository
import com.teamsparta.todoapp.domain.user.service.UserService
import jakarta.transaction.Transactional
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class CommentService(
    private val userService: UserService,
    private val commentRepository: CommentRepository,
    private val todoRepository: TodoRepository,
) {

    @Transactional
    fun createComment(todoId: Long, request: CreateCommentRequest): CommentResponse {
        val todo = todoRepository.findByIdOrNull(todoId)
            ?: throw ModelNotFoundException("Todo", todoId)

        return userService.getUserInfo()
            .let {
                commentRepository.save(
                    Comment(
                        comment = request.comment,
                        todo = todo,
                        nickname = it.nickname,
                        userEmail = it.userEmail
                    )).toResponse()
            }
    }

    @Transactional
    fun updateComment(commentId: Long, request: UpdateCommentRequest): CommentResponse {
        val comment = commentRepository.findByIdOrNull(commentId)
            ?: throw ModelNotFoundException("Comment", commentId)
        userService.getUserInfo()
            .let {
                if(comment.userEmail!=it.userEmail){
                    throw ArithmeticException("User email not Match")
                }
            }
        comment.comment = request.comment
        return comment.toResponse()
    }

    @Transactional
    fun deleteComment(commentId: Long) {
        val comment = commentRepository.findByIdOrNull(commentId)
            ?: throw ModelNotFoundException("Comment", commentId)
        userService.getUserInfo()
            .let {
                if(comment.userEmail!=it.userEmail){
                    throw ArithmeticException("User email not Match")
                }
            }
        commentRepository.delete(comment)
    }
}