package com.teamsparta.todoapp.domain.todo.model

import com.teamsparta.todoapp.domain.todo.comment.model.Comment
import com.teamsparta.todoapp.domain.todo.comment.model.toResponse
import com.teamsparta.todoapp.domain.todo.dto.TodoResponse
import com.teamsparta.todoapp.domain.todo.service.SortTodoSelector
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "todos")
class Todo (
    @Column(name = "title")
    var title: String,

    @Column(name = "description")
    var description: String,

    @Column(name = "created")
    var created:LocalDateTime = LocalDateTime.now(),

    @Column(name = "writer")
    var writer: String,

    @Column(name = "success")
    var success: Boolean=false,

    @OneToMany(fetch = FetchType.LAZY, cascade = [CascadeType.ALL], orphanRemoval = true)
    @JoinColumn(name="todo_id")
    val comments: MutableList<Comment> =mutableListOf(),
){
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
}

fun Todo.toResponse(commentList: List<Comment> = emptyList())
: TodoResponse {
    return TodoResponse(
        id = id!!,
        title = title,
        description = description,
        created = created,
        writer = writer,
        success = success,
        commentList = commentList.map { it.toResponse() }
    )
}