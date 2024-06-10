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
import jakarta.transaction.Transactional
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Slice
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

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
            ?: throw EntityNotFoundException("Todo $todoId not found")
    }

    @Transactional
    fun clearTodos() {
        todoRepository.deleteAll()
    }

    fun getAllTodoList(sortBy: SortTodoSelector, writer: String, page: Int): Slice<TodoResponse> {
        val pageable: Pageable = PageRequest.of(page, 5, sortBy.sort)
        val todoList = if (writer.isEmpty()) todoRepository.findAllWithSort(pageable)
        else todoRepository.findWriterWithSort(writer, pageable)
        return todoList.map { it.toResponse(it.comments) }
    }

    @Stopwatch
    fun getTodoById(todoId: Long): TodoResponse {
        return getTodoEntity(todoId).let { it.toResponse(it.comments) }
    }

    fun searchTodoList(title: String): List<TodoResponse> {
        return todoRepository.searchTodoListByTitle(title)
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
        todoRepository.delete(todo)
    }
}




