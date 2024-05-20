package com.teamsparta.todoapp.domain.todo.controller

import com.teamsparta.todoapp.domain.todo.dto.*
import com.teamsparta.todoapp.domain.todo.service.TodoService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RequestMapping("/todos")
@RestController
class TodoController(private val todoService: TodoService) {

    @GetMapping
    fun getTodoList(@RequestParam(defaultValue = "SUCCESS_ASC_DATE_DESC") sortBy: SortTodoSelector)
    : ResponseEntity<List<TodoResponse>>
    = ResponseEntity
        .status(HttpStatus.OK)
        .body(todoService.getAllTodoList(sortBy))

    @GetMapping("/{todoId}")
    fun getTodoById(@PathVariable todoId: Long)
    : ResponseEntity<TodoResponse>
    = ResponseEntity
        .status(HttpStatus.OK)
        .body(todoService.getTodoById(todoId))

    @PostMapping
    fun createTodo(@RequestBody createTodoRequest: CreateTodoRequest)
    :ResponseEntity<TodoResponse>
    = ResponseEntity
        .status(HttpStatus.CREATED)
        .body(todoService.createTodo(createTodoRequest))

    @PutMapping("/{todoId}")
    fun updateTodo(@PathVariable todoId: Long,
                   @RequestBody updateTodoRequest: UpdateTodoRequest)
    : ResponseEntity<TodoResponse>
    = ResponseEntity
        .status(HttpStatus.OK)
        .body(todoService.updateTodo(todoId,updateTodoRequest))

    @PatchMapping("/{todoId}/success")
    fun successTodo(@PathVariable todoId: Long)
            : ResponseEntity<TodoResponse>
            = ResponseEntity
        .status(HttpStatus.OK)
        .body(todoService.successTodo(todoId))

    @DeleteMapping("/{todoId}")
    fun deleteTodo(@PathVariable todoId: Long)
    :ResponseEntity<Unit>
    = ResponseEntity
        .status(HttpStatus.NO_CONTENT)
        .body(todoService.deleteTodo(todoId))
}