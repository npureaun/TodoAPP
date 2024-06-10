package com.teamsparta.todoapp.domain.todo.model

import com.teamsparta.todoapp.domain.todo.dto.comment.CommentResponse
import com.teamsparta.todoapp.domain.todo.dto.comment.CreateCommentRequest
import com.teamsparta.todoapp.domain.user.dto.UserResponse
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

    companion object {
        fun saveEntity(
            todo: Todo, request: CreateCommentRequest, userInfo: UserResponse): Comment {
            return Comment(
                comment = request.comment,
                nickname = userInfo.nickname,
                userEmail = userInfo.userEmail,
                todo = todo,
            )
        }
    }
}

fun Comment.toResponse(): CommentResponse {
    return CommentResponse(
        id = id!!,
        nickname = nickname,
        comment = comment,
    )
}