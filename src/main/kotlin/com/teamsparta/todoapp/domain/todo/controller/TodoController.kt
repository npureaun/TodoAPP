package com.teamsparta.todoapp.domain.todo.controller

import com.teamsparta.todoapp.domain.todo.dto.*
import com.teamsparta.todoapp.domain.todo.service.SortTodoSelector
import com.teamsparta.todoapp.domain.todo.service.TodoService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.*

@RequestMapping("/todos")
@RestController
class TodoController(private val todoService: TodoService) {

    @GetMapping
    fun getTodoList(
        @RequestParam(defaultValue = "SUCCESS_ASC_DATE_DESC") sortBy: SortTodoSelector,
        @RequestParam(defaultValue = "") writer:String
    )
    : ResponseEntity<List<TodoResponse>>{
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(todoService.getAllTodoList(sortBy,writer))
    }

    @GetMapping("/{todoId}")
    fun getTodoById(@PathVariable todoId: Long)
    : ResponseEntity<TodoResponse>{
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(todoService.getTodoById(todoId))
    }

    @PostMapping
    fun createTodo(@Valid @RequestBody createTodoRequest: CreateTodoRequest,
        bindingResult: BindingResult)
    :ResponseEntity<TodoResponse>{
        if(bindingResult.hasErrors()){
            return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .build()
        }
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(todoService.createTodo(createTodoRequest))
    }

    @PutMapping("/{todoId}")
    fun updateTodo(@PathVariable todoId: Long,
                   @Valid @RequestBody updateTodoRequest: UpdateTodoRequest,
                   bindingResult: BindingResult)
    : ResponseEntity<TodoResponse> {
        if(bindingResult.hasErrors()){
            return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .build()
        }
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(todoService.updateTodo(todoId, updateTodoRequest))
    }

    @PatchMapping("/{todoId}/success")
    fun successTodo(@PathVariable todoId: Long)
            : ResponseEntity<TodoResponse> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(todoService.successTodo(todoId))
    }

    @DeleteMapping("/{todoId}")
    fun deleteTodo(@PathVariable todoId: Long)
    :ResponseEntity<Unit> {
        return ResponseEntity
            .status(HttpStatus.NO_CONTENT)
            .body(todoService.deleteTodo(todoId))
    }
}