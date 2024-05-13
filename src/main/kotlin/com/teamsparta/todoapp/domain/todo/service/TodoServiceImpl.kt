package com.teamsparta.todoapp.domain.todo.service

import com.teamsparta.todoapp.domain.todo.dto.CreateTodoRequest
import com.teamsparta.todoapp.domain.todo.dto.TodoResponse
import com.teamsparta.todoapp.domain.todo.dto.UpdateTodoRequest
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
class TodoServiceImpl:TodoService {
    override fun getAllTodoList(): List<TodoResponse> {
        TODO("Not yet implemented")
    }

    override fun getTodoById(todoId: Long): TodoResponse {
        TODO("Not yet implemented")
    }

    @Transactional
    override fun createTodo(request: CreateTodoRequest): TodoResponse {
        TODO("Not yet implemented")
    }

    @Transactional
    override fun updateTodo(todoId: Long, request: UpdateTodoRequest): TodoResponse {
        TODO("Not yet implemented")
    }

    @Transactional
    override fun deleteTodo(todoId: Long): TodoResponse {
        TODO("Not yet implemented")
    }
}




