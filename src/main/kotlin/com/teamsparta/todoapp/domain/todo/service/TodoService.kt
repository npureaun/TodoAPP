package com.teamsparta.todoapp.domain.todo.service

import com.teamsparta.todoapp.domain.todo.dto.*

interface TodoService {
    fun getAllTodoList(sortBy: SortTodoSelector): List<TodoResponse>
    fun getTodoById(todoId: Long): TodoResponse
    fun createTodo(request: CreateTodoRequest): TodoResponse
    fun updateTodo(todoId: Long, request: UpdateTodoRequest): TodoResponse
    fun successTodo(todoId: Long): TodoResponse
    fun deleteTodo(todoId: Long)
}