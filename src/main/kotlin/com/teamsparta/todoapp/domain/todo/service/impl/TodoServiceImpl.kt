package com.teamsparta.todoapp.domain.todo.service.impl

import com.teamsparta.todoapp.domain.todo.repository.CommentRepository
import com.teamsparta.todoapp.domain.exception.ModelNotFoundException
import com.teamsparta.todoapp.domain.security.jwt.JwtUtil
import com.teamsparta.todoapp.domain.todo.dto.todo.CreateTodoRequest
import com.teamsparta.todoapp.domain.todo.dto.todo.TodoResponse
import com.teamsparta.todoapp.domain.todo.dto.todo.UpdateTodoRequest
import com.teamsparta.todoapp.domain.todo.dto.todo.UserTokenRequest
import com.teamsparta.todoapp.domain.todo.model.Comment
import com.teamsparta.todoapp.domain.todo.model.Todo
import com.teamsparta.todoapp.domain.todo.model.toResponse
import com.teamsparta.todoapp.domain.todo.repository.TodoRepository
import com.teamsparta.todoapp.domain.todo.service.SortTodoSelector
import com.teamsparta.todoapp.domain.todo.service.TodoService
import com.teamsparta.todoapp.domain.user.service.UserService
import jakarta.transaction.Transactional
import org.hibernate.service.spi.ServiceException
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Slice
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import javax.naming.AuthenticationException

@Service
class TodoServiceImpl(
    private val todoRepository: TodoRepository,
    private val commentRepository: CommentRepository,
): TodoService {

    override fun clearTodos(){
        todoRepository.deleteAll()
    }

    override fun getAllTodoList(sortBy: SortTodoSelector, writer:String, page:Int): Slice<TodoResponse> {
        val pageable:Pageable = PageRequest.of(page,5, sortBy.sort)
        val todoList = if (writer.isEmpty()) todoRepository.findAllWithSort(pageable)
        else todoRepository.findWriterWithSort(writer,pageable)
        if (todoList.isEmpty) {
            throw ModelNotFoundException("Todo", 0)
        }
        return todoList.map { it.toResponse(it.comments) }
    }

    override fun getTodoById(todoId: Long): TodoResponse {
        val todo = todoRepository.findByIdOrNull(todoId)
            ?: throw ModelNotFoundException("Todo", todoId)
        return todo.toResponse(todo.comments)
    }

    @Transactional
    override fun createTodo(request: CreateTodoRequest): TodoResponse {
        return todoRepository.save(
            Todo(
                title = request.title,
                description = request.description,
                writer = request.writer,
                userId = JwtUtil.getUserIdFromToken(request.token!!)!!
            )
        ).toResponse()
    }

    @Transactional
    override fun updateTodo(todoId: Long, request: UpdateTodoRequest): TodoResponse {
        val todo = todoRepository.findByIdOrNull(todoId)
            ?: throw ModelNotFoundException("Todo", todoId)

        if(!todoRepository.matchingByUserIdOrBool(todoId,
                JwtUtil.getUserIdFromToken(request.token!!)!!)){
            throw AuthenticationException("User Id Not Match")
        }
        with(todo) {
            title = request.title
            description = request.description
            writer = request.writer
        }
        return todo.toResponse()
    }

    @Transactional
    override fun successTodo(todoId: Long,request: UserTokenRequest): TodoResponse {
        val todo = todoRepository.findByIdOrNull(todoId)
            ?: throw ModelNotFoundException("Todo", todoId)
        if(!todoRepository.matchingByUserIdOrBool(todoId,
                JwtUtil.getUserIdFromToken(request.token!!)!!)){
            throw AuthenticationException("User Id Not Match")
        }

        todo.success = !todo.success
        return todo.toResponse()
    }

    @Transactional
    override fun deleteTodo(todoId: Long,request: UserTokenRequest) {
        val todo = todoRepository.findByIdOrNull(todoId)
            ?: throw ModelNotFoundException("Todo", todoId)
        if(!todoRepository.matchingByUserIdOrBool(todoId,
                JwtUtil.getUserIdFromToken(request.token!!)!!)){
            throw AuthenticationException("User Id Not Match")
        }
        todoRepository.delete(todo)
    }
}




