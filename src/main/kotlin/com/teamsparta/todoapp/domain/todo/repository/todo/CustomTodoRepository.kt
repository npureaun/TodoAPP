package com.teamsparta.todoapp.domain.todo.repository.todo

import com.teamsparta.todoapp.domain.todo.model.Todo

interface CustomTodoRepository {
    fun searchCourseListByTitle(title: String): List<Todo>
}