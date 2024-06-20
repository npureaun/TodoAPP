package com.teamsparta.todoapp.domain.todo.repository.todo

import com.teamsparta.todoapp.domain.todo.model.Todo
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Slice

interface CustomTodoRepository {
    fun searchTodoListByTitle(title: String?): List<Todo>

    //fun findTodoListByNickname(nickname: String, pageable: Pageable): Slice<Todo>
}