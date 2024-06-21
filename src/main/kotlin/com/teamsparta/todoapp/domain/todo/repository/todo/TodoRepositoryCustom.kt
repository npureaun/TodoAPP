package com.teamsparta.todoapp.domain.todo.repository.todo

import com.teamsparta.todoapp.domain.todo.model.Todo
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface TodoRepositoryCustom {
    fun searchTodoListByTitle(pageable: Pageable, title: String?): List<Todo>

    //fun findTodoListByNickname(nickname: String, pageable: Pageable): Slice<Todo>
}