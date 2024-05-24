package com.teamsparta.todoapp.domain.security.encode

import org.mindrot.jbcrypt.BCrypt

object BCHash {
    fun hashPassword(password: String): String {
       return BCrypt.hashpw(password,BCrypt.gensalt())
    }

    fun verifyPassword(inputPassword: String, hashedPassword: String): Boolean {
        return BCrypt.checkpw(inputPassword, hashedPassword)
    }
}