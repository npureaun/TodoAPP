package com.teamsparta.todoapp.domain.todo.repository

import com.teamsparta.todoapp.domain.todo.model.Todo
import org.springframework.data.jpa.repository.JpaRepository

interface TodoRepository:JpaRepository<Todo, Long> {
    fun findAllByOrderBySuccessAscCreatedDateDesc(): List<Todo>
}