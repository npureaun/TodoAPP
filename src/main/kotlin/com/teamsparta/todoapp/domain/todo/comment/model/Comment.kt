package com.teamsparta.todoapp.domain.todo.comment.model

import com.teamsparta.todoapp.domain.todo.comment.dto.CommentResponse
import com.teamsparta.todoapp.domain.todo.model.Todo
import jakarta.persistence.*

@Entity
@Table(name = "comments")
class Comment(
    @Column(name = "writer")
    var writer: String,
    @Column(name = "password")
    var password: String,
    @Column(name = "comment")
    var comment: String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="todo_id")
    val todo: Todo,
) {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
}

fun Comment.toResponse(): CommentResponse {
    return CommentResponse(
        id = id!!,
        writer = writer,
        comment = comment,
    )
}