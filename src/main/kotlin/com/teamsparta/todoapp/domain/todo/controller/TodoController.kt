package com.teamsparta.todoapp.domain.todo.controller

import com.teamsparta.todoapp.domain.todo.dto.todo.CreateTodoRequest
import com.teamsparta.todoapp.domain.todo.dto.todo.TodoResponse
import com.teamsparta.todoapp.domain.todo.dto.todo.UpdateTodoRequest
import com.teamsparta.todoapp.domain.todo.service.TodoService
import jakarta.validation.Valid
import org.springframework.core.MethodParameter
import org.springframework.data.domain.*
import org.springframework.data.web.PageableDefault
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.validation.BindingResult
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.*

@RequestMapping("/todos")
@RestController
class TodoController(private val todoService: TodoService) {

    @GetMapping
    fun getTodoList(
        @PageableDefault(size = 15) pageable: Pageable,
        @RequestParam(required = false,name = "title") title: String?,
        @RequestParam(required = false,name = "tag") tag: String?,
        @RequestParam(required = false, name = "direction", defaultValue = "DESC") direction: Sort.Direction,
    ) : ResponseEntity<Page<TodoResponse>> {
        val sortPageable=pageable.sort
            .map { Sort.Order(direction, it.property) }
            .let { Sort.by(it.toList()) }
            .let { PageRequest.of(pageable.pageNumber, pageable.pageSize, it) }

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(todoService.searchTodoList(sortPageable,title,tag))
    }

    @GetMapping("/{todoId}")
    @PreAuthorize("hasRole('STANDARD') or hasRole('DEVELOP')")
    fun getTodoById(@PathVariable todoId: Long)
            : ResponseEntity<TodoResponse> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(todoService.getTodoById(todoId))
    }

    @PostMapping
    @PreAuthorize("hasRole('STANDARD') or hasRole('DEVELOP')")
    fun createTodo(
        @Valid @RequestBody createTodoRequest: CreateTodoRequest,
        bindingResult: BindingResult)
            : ResponseEntity<TodoResponse> {
        if (bindingResult.hasErrors()) {
            val methodParameter = MethodParameter(
                this::class.java.getMethod(
                    "createTodo"
                    , CreateTodoRequest::class.java
                    , BindingResult::class.java
                ),
                1)
            throw MethodArgumentNotValidException(methodParameter, bindingResult)
        }

        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(todoService.createTodo(createTodoRequest))
    }

    @PutMapping("/{todoId}")
    @PreAuthorize("hasRole('STANDARD') or hasRole('DEVELOP')")
    fun updateTodo(
        @PathVariable todoId: Long,
        @Valid @RequestBody updateTodoRequest: UpdateTodoRequest,
        bindingResult: BindingResult)
            : ResponseEntity<TodoResponse> {
        if (bindingResult.hasErrors()) {
            val methodParameter = MethodParameter(
                this::class.java.getMethod(
                    "updateTodo"
                    , Long::class.java
                    , UpdateTodoRequest::class.java
                    , BindingResult::class.java
                ),
                2)
            throw MethodArgumentNotValidException(methodParameter, bindingResult)
        }

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(todoService.updateTodo(todoId, updateTodoRequest))
    }

    @PatchMapping("/{todoId}/success")
    @PreAuthorize("hasRole('STANDARD') or hasRole('DEVELOP')")
    fun successTodo(@PathVariable todoId: Long)
            : ResponseEntity<TodoResponse> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(todoService.successTodo(todoId))
    }

    @DeleteMapping("/{todoId}")
    @PreAuthorize("hasRole('STANDARD') or hasRole('DEVELOP')")
    fun deleteTodo(@PathVariable todoId: Long)
            : ResponseEntity<Unit> {
        return ResponseEntity
            .status(HttpStatus.NO_CONTENT)
            .body(todoService.deleteTodo(todoId))
    }
}


