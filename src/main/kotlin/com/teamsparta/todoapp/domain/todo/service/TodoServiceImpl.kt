package com.teamsparta.todoapp.domain.todo.service

import com.teamsparta.todoapp.domain.exception.ModelNotFoundException
import com.teamsparta.todoapp.domain.todo.dto.CreateTodoRequest
import com.teamsparta.todoapp.domain.todo.dto.TodoResponse
import com.teamsparta.todoapp.domain.todo.dto.UpdateTodoRequest
import com.teamsparta.todoapp.domain.todo.model.Todo
import com.teamsparta.todoapp.domain.todo.model.toResponse
import com.teamsparta.todoapp.domain.todo.repository.TodoRepository
import jakarta.transaction.Transactional
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class TodoServiceImpl(
    private val todoRepository: TodoRepository
):TodoService {

    override fun getAllTodoList(): List<TodoResponse> {
        val todoList = todoRepository.findAllByOrderBySuccessAscCreatedDateDesc()
        if(todoList.isEmpty()) { throw ModelNotFoundException("Todo",0) }
        return todoList.map { it.toResponse() }
    }

    override fun getTodoById(todoId: Long): TodoResponse {
        val todo=todoRepository.findByIdOrNull(todoId)
            ?: throw ModelNotFoundException("Todo", todoId)
        return todo.toResponse()
    }

    @Transactional
    override fun createTodo(request: CreateTodoRequest): TodoResponse {
        return todoRepository.save(
            Todo(
                title = request.title,
                description = request.description,
                writer = request.writer,
                createdDate = LocalDateTime.now(),
                success = false
            )
        ).toResponse()
    }

    @Transactional
    override fun updateTodo(todoId: Long, request: UpdateTodoRequest): TodoResponse {
        val todo=todoRepository.findByIdOrNull(todoId)
            ?: throw ModelNotFoundException("Todo", todoId)
        todo.title=request.title
        todo.description=request.description
        todo.writer=request.writer
        todo.createdDate=LocalDateTime.now()
        return todoRepository.save(todo).toResponse()
    }

    @Transactional
    override fun successTodo(todoId: Long): TodoResponse {
        val todo=todoRepository.findByIdOrNull(todoId)
            ?: throw ModelNotFoundException("Todo", todoId)
        todo.success=!todo.success
        return todoRepository.save(todo).toResponse()
    }

    @Transactional
    override fun deleteTodo(todoId: Long) {
        val todo=todoRepository.findByIdOrNull(todoId)
            ?: throw ModelNotFoundException("Todo", todoId)
        todoRepository.delete(todo)
    }
}




