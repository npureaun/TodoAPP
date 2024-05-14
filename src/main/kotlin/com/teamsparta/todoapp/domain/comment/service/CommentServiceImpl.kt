package com.teamsparta.todoapp.domain.comment.service

import com.teamsparta.todoapp.domain.comment.dto.CommentResponse
import com.teamsparta.todoapp.domain.comment.dto.CreateCommentRequest
import com.teamsparta.todoapp.domain.comment.model.Comment
import com.teamsparta.todoapp.domain.comment.model.toResponse
import com.teamsparta.todoapp.domain.comment.repository.CommentRepository
import com.teamsparta.todoapp.domain.exception.ModelNotFoundException
import com.teamsparta.todoapp.domain.todo.repository.TodoRepository
import jakarta.transaction.Transactional
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class CommentServiceImpl(
    private val commentRepository: CommentRepository,
    private val todoRepository: TodoRepository,
): CommentService {
    override fun getAllComment()
            : List<CommentResponse>{
        TODO("Not yet implemented")
    }

    @Transactional
    override fun createComment(todoId: Long,request: CreateCommentRequest): CommentResponse {
        val todo=todoRepository.findByIdOrNull(todoId)
            ?: throw ModelNotFoundException("Todo", todoId)

        val comment=Comment(
            writer = request.writer,
            password = request.passWord,
            comment = request.comment,
            todo = todo
        )

        commentRepository.save(comment)
        return comment.toResponse()
    }
}