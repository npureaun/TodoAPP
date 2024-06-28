package com.teamsparta.todoapp.domain.todo.service

import com.teamsparta.todoapp.domain.todo.dto.todo.CreateTodoRequest
import com.teamsparta.todoapp.domain.todo.dto.todo.TodoResponse
import com.teamsparta.todoapp.domain.todo.dto.todo.UpdateTodoRequest
import com.teamsparta.todoapp.domain.todo.model.Todo
import com.teamsparta.todoapp.domain.todo.model.toResponse
import com.teamsparta.todoapp.domain.todo.repository.todo.TodoRepository
import com.teamsparta.todoapp.domain.user.service.UserService
import com.teamsparta.todoapp.infra.aop.Stopwatch
import jakarta.persistence.EntityNotFoundException
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class TodoService(
    private val userService: UserService,
    private val todoRepository: TodoRepository,
) {

    private fun userChecking(comparison:String) {
        userService.getUserInfo()
            .let {
                if (it.userEmail != comparison) {
                    throw IllegalArgumentException("User Not Match")
                }
            }
    }

    fun getTodoEntity(todoId: Long): Todo {
        return todoRepository.findByIdOrNull(todoId)
            ?.let {
                if(it.isDelete) throw IllegalStateException("Todo $todoId is Delete")
                else it
            }?:throw EntityNotFoundException("Todo $todoId not found")
    }

    fun getTodoById(todoId: Long): TodoResponse {
        return getTodoEntity(todoId).let { it.toResponse(it.comments) }
    }

    @Stopwatch
    fun searchTodoList(pageable: Pageable,title: String?, tag:String?): Page<TodoResponse> {
        return todoRepository.searchTodoListByTitle(pageable,title,tag)
            .map { it.toResponse(it.comments) }
    }

    @Transactional
    fun createTodo(request: CreateTodoRequest): TodoResponse {
        return userService.getUserInfo()
            .let { todoRepository.save(Todo.saveEntity(request,it)) }
            .toResponse()
    }

    @Transactional
    fun updateTodo(todoId: Long, request: UpdateTodoRequest): TodoResponse {
        val todo = getTodoEntity(todoId)
        userChecking(todo.userEmail)
        with(todo) {
            title = request.title
            description = request.description
        }
        return todo.toResponse()
    }

    @Transactional
    fun successTodo(todoId: Long): TodoResponse {
        val todo = getTodoEntity(todoId)
       userChecking(todo.userEmail)

        todo.success = !todo.success
        return todo.toResponse()
    }

    @Transactional
    fun deleteTodo(todoId: Long) {
        val todo = getTodoEntity(todoId)
        userChecking(todo.userEmail)

        todo.isDelete=true
    }
}




