package com.teamsparta.todoapp.domain.todo.controller

import com.teamsparta.todoapp.domain.todo.dto.todo.CreateTodoRequest
import com.teamsparta.todoapp.domain.todo.dto.todo.TodoResponse
import com.teamsparta.todoapp.domain.todo.dto.todo.UpdateTodoRequest
import com.teamsparta.todoapp.domain.todo.service.SortTodoSelector
import com.teamsparta.todoapp.domain.todo.service.TodoService
import jakarta.validation.Valid
import org.springframework.core.MethodParameter
import org.springframework.data.domain.Slice
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.userdetails.User
import org.springframework.validation.BindingResult
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.*

@RequestMapping("/todos")
@RestController
class TodoController(private val todoService: TodoService) {

    @GetMapping("/search")
    fun searchCourseList(@RequestParam(name = "title") title: String)
            : ResponseEntity<List<TodoResponse>> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(todoService.searchTodoList(title))
    }

    @GetMapping()
    fun getTodoList(
        @RequestParam(defaultValue = "SUCCESS_ASC_DATE_DESC") sortBy: SortTodoSelector,
        @RequestParam(defaultValue = "") writer: String,
        @RequestParam(defaultValue = "0") page: Int,
    )
            : ResponseEntity<Slice<TodoResponse>> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(todoService.getAllTodoList(sortBy, writer, page))
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
        bindingResult: BindingResult,
//        @AuthenticationPrincipal user: User
    )
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

    @DeleteMapping
    @PreAuthorize("hasRole('DEVELOP')")
    fun clearTodos()
            : ResponseEntity<Unit> {
        return ResponseEntity
            .status(HttpStatus.NO_CONTENT)
            .body(todoService.clearTodos())
    }


    @GetMapping("/user")
    @PreAuthorize("hasRole('DEVELOP')")
    fun getUser(@AuthenticationPrincipal user: User): String {
        return "Hello, ${user.username}!"
    }
}