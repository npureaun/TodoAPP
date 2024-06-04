package com.teamsparta.todoapp.domain.todo.service

import com.teamsparta.todoapp.domain.exception.ModelNotFoundException
import com.teamsparta.todoapp.domain.todo.dto.todo.CreateTodoRequest
import com.teamsparta.todoapp.domain.todo.dto.todo.TodoResponse
import com.teamsparta.todoapp.domain.todo.dto.todo.UpdateTodoRequest
import com.teamsparta.todoapp.domain.todo.model.Todo
import com.teamsparta.todoapp.domain.todo.model.toResponse
import com.teamsparta.todoapp.domain.todo.repository.todo.TodoRepository
import com.teamsparta.todoapp.domain.user.service.UserService
import com.teamsparta.todoapp.infra.aop.Stopwatch
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

    fun clearTodos() {
        todoRepository.deleteAll()
    }

    fun getAllTodoList(sortBy: SortTodoSelector, writer: String, page: Int): Slice<TodoResponse> {
        val pageable: Pageable = PageRequest.of(page, 5, sortBy.sort)
        val todoList = if (writer.isEmpty()) todoRepository.findAllWithSort(pageable)
        else todoRepository.findWriterWithSort(writer, pageable)
        if (todoList.isEmpty) {
            throw ModelNotFoundException("Todo", 0)
        }
        return todoList.map { it.toResponse(it.comments) }
    }

    @Stopwatch
    fun getTodoById(todoId: Long): TodoResponse {
        val todo = todoRepository.findByIdOrNull(todoId)
            ?: throw ModelNotFoundException("Todo", todoId)
        return todo.toResponse(todo.comments)
    }

    @Transactional
    fun createTodo(request: CreateTodoRequest): TodoResponse {
        return userService.getUserInfo()
            .let {
                todoRepository.save(
                    Todo(
                        title = request.title,
                        description = request.description,
                        nickname = it.nickname,
                        userEmail = it.userEmail
                    )
                ).toResponse()
            }
    }

    @Transactional
    fun updateTodo(todoId: Long, request: UpdateTodoRequest): TodoResponse {
        val todo = todoRepository.findByIdOrNull(todoId)
            ?: throw ModelNotFoundException("Todo", todoId)
        userService.getUserInfo()
            .let {
                if(it.nickname!=todo.nickname){
                    throw ArithmeticException("User Not Match")
                }
            }
        with(todo) {
            title = request.title
            description = request.description
        }
        return todo.toResponse()
    }

    @Transactional
    fun successTodo(todoId: Long): TodoResponse {
        val todo = todoRepository.findByIdOrNull(todoId)
            ?: throw ModelNotFoundException("Todo", todoId)
        userService.getUserInfo()
            .let {
                if(it.nickname!=todo.nickname){
                    throw ArithmeticException("User Not Match")
                }
            }
        todo.success = !todo.success
        return todo.toResponse()
    }

    @Transactional
    fun deleteTodo(todoId: Long) {
        val todo = todoRepository.findByIdOrNull(todoId)
            ?: throw ModelNotFoundException("Todo", todoId)
        userService.getUserInfo()
            .let {
                if(it.nickname!=todo.nickname){
                    throw ArithmeticException("User Not Match")
                }
            }
        todoRepository.delete(todo)
    }

    fun searchCourseList(title: String): List<TodoResponse> {
        return todoRepository.searchCourseListByTitle(title).map { it.toResponse() }
    }
}




