package com.teamsparta.todoapp.domain.todo.repository.todo

import com.querydsl.core.BooleanBuilder
import com.teamsparta.todoapp.domain.todo.model.Todo
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Slice

interface TodoRepositoryCustom {
    fun searchTodoListByTitle(pageable: Pageable, title: String?, tag:String?): Page<Todo>

    fun deleteByIsDelete()
    //fun findTodoListByNickname(nickname: String, pageable: Pageable): Slice<Todo>
}