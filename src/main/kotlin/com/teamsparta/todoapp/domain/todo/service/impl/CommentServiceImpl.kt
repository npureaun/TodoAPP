package com.teamsparta.todoapp.domain.todo.service.impl

import com.teamsparta.todoapp.domain.todo.dto.comment.CommentResponse
import com.teamsparta.todoapp.domain.todo.dto.comment.CreateCommentRequest
import com.teamsparta.todoapp.domain.todo.dto.comment.DeleteCommentRequest
import com.teamsparta.todoapp.domain.todo.dto.comment.UpdateCommentRequest
import com.teamsparta.todoapp.domain.todo.model.Comment
import com.teamsparta.todoapp.domain.todo.model.toResponse
import com.teamsparta.todoapp.domain.todo.repository.CommentRepository
import com.teamsparta.todoapp.domain.exception.ModelNotFoundException
import com.teamsparta.todoapp.domain.exception.PasswordUnMatchingException
import com.teamsparta.todoapp.domain.todo.repository.TodoRepository
import com.teamsparta.todoapp.domain.todo.service.CommentService
import jakarta.transaction.Transactional
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class CommentServiceImpl(
    private val commentRepository: CommentRepository,
    private val todoRepository: TodoRepository,
): CommentService {

    @Transactional
    override fun createComment(todoId: Long,request: CreateCommentRequest): CommentResponse {
        val todo=todoRepository.findByIdOrNull(todoId)
            ?: throw ModelNotFoundException("Todo", todoId)

        val comment= Comment(
            writer = request.writer,
            password = request.passWord,
            comment = request.comment,
            todo=todo,
        )

        commentRepository.save(comment)
        return comment.toResponse()
    }

    @Transactional
    override fun updateComment(commentId:Long, request: UpdateCommentRequest): CommentResponse {
        val comment= commentRepository.findByIdOrNull(commentId)
            ?: throw ModelNotFoundException("Comment", commentId)
        if(request.writer != comment.writer
            || request.passWord!=comment.password){
            throw PasswordUnMatchingException("UPDATE")
        }

        comment.comment = request.comment
        return comment.toResponse()
    }

    @Transactional
    override fun deleteComment(commentId: Long,request: DeleteCommentRequest) {
        val comment= commentRepository.findByIdOrNull(commentId)
            ?: throw ModelNotFoundException("Comment", commentId)
        if(request.writer != comment.writer
            || request.passWord!=comment.password){
            throw PasswordUnMatchingException("DELETE")
        }

        commentRepository.delete(comment)
    }
}