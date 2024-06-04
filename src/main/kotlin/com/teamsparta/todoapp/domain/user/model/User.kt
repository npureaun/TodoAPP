package com.teamsparta.todoapp.domain.user.model

import com.teamsparta.todoapp.domain.user.dto.UserResponse
import jakarta.persistence.*

@Entity
@Table(name = "users")
class User(
    @Column(name = "user_email")
    var userEmail: String,
    @Column(name = "user_password")
    var userPassword: String,
    @Embedded
    var profile: Profile,
    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    val role: UserRole,
) {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
}

fun User.toResponse(): UserResponse {
    return UserResponse(
        id = id!!,
        nickname = profile.nickname,
        userEmail = userEmail,
        role = role.name
    )
}