package com.teamsparta.todoapp.domain.todo.service

import com.teamsparta.todoapp.domain.todo.dto.CreateTodoRequest
import com.teamsparta.todoapp.domain.todo.dto.TodoResponse
import com.teamsparta.todoapp.domain.todo.dto.UpdateTodoRequest

interface TodoService {
    fun getAllTodoList(): List<TodoResponse>
    fun getTodoById(todoId: Long): TodoResponse
    fun createTodo(request: CreateTodoRequest): TodoResponse
    fun updateTodo(todoId: Long, request: UpdateTodoRequest): TodoResponse
    fun deleteTodo(todoId: Long)
}