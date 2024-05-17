package com.teamsparta.todoapp.domain.exception

data class PasswordUnMatchingException(val process: String):RuntimeException(
    "$process : Passwords do not match"
)
