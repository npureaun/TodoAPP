package com.teamsparta.todoapp.domain.todo.comment.repository

import com.teamsparta.todoapp.domain.todo.comment.model.Comment
import org.springframework.data.jpa.repository.JpaRepository

interface CommentRepository: JpaRepository<Comment, Long> {
    fun findAllByTodoId(todoId:Long): List<Comment>
}