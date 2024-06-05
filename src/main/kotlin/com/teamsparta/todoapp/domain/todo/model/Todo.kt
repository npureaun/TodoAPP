package com.teamsparta.todoapp.domain.todo.model

import com.teamsparta.todoapp.domain.todo.dto.comment.CreateCommentRequest
import com.teamsparta.todoapp.domain.todo.dto.todo.CreateTodoRequest
import com.teamsparta.todoapp.domain.todo.dto.todo.TodoResponse
import com.teamsparta.todoapp.domain.user.dto.UserResponse
import com.teamsparta.todoapp.domain.user.model.User
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

    @Column(name = "success")
    var success: Boolean=false,

    @Column(name = "nickname")
    var nickname: String,

    @OneToMany(fetch = FetchType.LAZY, cascade = [CascadeType.ALL], orphanRemoval = true, mappedBy = "todo")
    val comments: MutableList<Comment> =mutableListOf(),

    val userEmail: String,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    companion object {
        fun saveEntity(request: CreateTodoRequest, userInfo: UserResponse): Todo {
            return Todo(
                title = request.title,
                description = request.description,
                nickname = userInfo.nickname,
                userEmail = userInfo.userEmail
            )
        }
    }
}

fun Todo.toResponse(commentList: List<Comment> = emptyList())
: TodoResponse {
    return TodoResponse(
        id = id!!,
        title = title,
        description = description,
        created = created.toString(),
        nickname = nickname,
        success = success,
        commentList = commentList.map { it.toResponse() }
    )
}