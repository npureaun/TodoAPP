package com.teamsparta.todoapp.domain.todo.repository.comment

import com.teamsparta.todoapp.domain.todo.model.Comment
import org.springframework.data.jpa.repository.JpaRepository

interface CommentRepository: JpaRepository<Comment, Long> {
}