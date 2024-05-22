package com.teamsparta.todoapp.domain.todo.service

import com.teamsparta.todoapp.domain.todo.dto.todo.CreateTodoRequest
import com.teamsparta.todoapp.domain.todo.dto.todo.TodoResponse
import com.teamsparta.todoapp.domain.todo.dto.todo.UpdateTodoRequest
import org.springframework.data.domain.Slice

interface TodoService {
    fun getAllTodoList(sortBy: SortTodoSelector,writer:String, page:Int): Slice<TodoResponse>
    fun getTodoById(todoId: Long): TodoResponse
    fun createTodo(request: CreateTodoRequest): TodoResponse
    fun updateTodo(todoId: Long, request: UpdateTodoRequest): TodoResponse
    fun successTodo(todoId: Long): TodoResponse
    fun deleteTodo(todoId: Long)
    fun clearTodos()
}