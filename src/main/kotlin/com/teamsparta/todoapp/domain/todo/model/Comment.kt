package com.teamsparta.todoapp.domain.todo.model

import com.teamsparta.todoapp.domain.todo.dto.comment.CommentResponse
import com.teamsparta.todoapp.domain.user.service.UserService
import jakarta.persistence.*

@Entity
@Table(name = "comments")
class Comment(
    @Column(name = "comment")
    var comment: String,

    @Column(name = "nickname")
    var nickname: String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="todo_id")
    val todo: Todo,

    val userEmail:String
) {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
}

fun Comment.toResponse(): CommentResponse {
    return CommentResponse(
        id = id!!,
        nickname = nickname,
        comment = comment,
    )
}