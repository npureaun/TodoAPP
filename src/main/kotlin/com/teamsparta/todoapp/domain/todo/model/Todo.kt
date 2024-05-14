package com.teamsparta.todoapp.domain.todo.model

import com.teamsparta.todoapp.domain.todo.dto.TodoResponse
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "todo_list")
class Todo (
    @Column(name = "title")
    var title: String,

    @Column(name = "description")
    var description: String,

    @Column(name = "created_date")
    var createdDate: LocalDateTime,

    @Column(name = "writer")
    var writer: String,
){
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
}

fun Todo.toResponse(): TodoResponse {
    return TodoResponse(
        id = id!!,
        title = title,
        description = description,
        createdDate = createdDate,
        writer = writer,
    )
}