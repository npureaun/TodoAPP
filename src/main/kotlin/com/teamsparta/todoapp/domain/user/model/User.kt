package com.teamsparta.todoapp.domain.user.model

import jakarta.persistence.*

@Entity
@Table(name = "users")
class User(
    @Column(name = "user_id")
    var userId: String,
    @Column(name = "user_password")
    var userPassword: String,
) {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
}