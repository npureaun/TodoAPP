package com.teamsparta.todoapp.domain.todo.service

import com.teamsparta.todoapp.domain.todo.comment.repository.CommentRepository
import com.teamsparta.todoapp.domain.exception.ModelNotFoundException
import com.teamsparta.todoapp.domain.todo.dto.*
import com.teamsparta.todoapp.domain.todo.model.Todo
import com.teamsparta.todoapp.domain.todo.model.toResponse
import com.teamsparta.todoapp.domain.todo.repository.TodoRepository
import jakarta.transaction.Transactional
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class TodoServiceImpl(
    private val todoRepository: TodoRepository,
    private val commentRepository: CommentRepository,
):TodoService {

    override fun getAllTodoList(sortBy: SortTodoSelector, writer:String): List<TodoResponse> {
        val todoList =
            if(writer.isEmpty())
                todoRepository.findAllWithSort(sortBy.sort)
            else
                todoRepository.findWriterWithSort(sortBy.sort,writer)


        if (todoList.isEmpty()) {
            throw ModelNotFoundException("Todo", 0)
        }
        return todoList.map { it.toResponse() }
    }

    override fun getTodoById(todoId: Long): TodoResponse {
        val todo = todoRepository.findByIdOrNull(todoId)
            ?: throw ModelNotFoundException("Todo", todoId)
        return todo.toResponse(commentRepository.findAllByTodoId(todoId))
    }

    @Transactional
    override fun createTodo(request: CreateTodoRequest): TodoResponse {
        return todoRepository.save(
            Todo(
                title = request.title,
                description = request.description,
                writer = request.writer,
            )
        ).toResponse()
    }

    @Transactional
    override fun updateTodo(todoId: Long, request: UpdateTodoRequest): TodoResponse {
        val todo = todoRepository.findByIdOrNull(todoId)
            ?: throw ModelNotFoundException("Todo", todoId)
        with(todo) {
            title = request.title
            description = request.description
            writer = request.writer
        }
        return todo.toResponse()
    }

    @Transactional
    override fun successTodo(todoId: Long): TodoResponse {
        val todo = todoRepository.findByIdOrNull(todoId)
            ?: throw ModelNotFoundException("Todo", todoId)
        todo.success = !todo.success
        return todo.toResponse()
    }

    @Transactional
    override fun deleteTodo(todoId: Long) {
        val todo = todoRepository.findByIdOrNull(todoId)
            ?: throw ModelNotFoundException("Todo", todoId)
        todoRepository.delete(todo)
    }
}




