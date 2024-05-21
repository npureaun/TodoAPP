package com.teamsparta.todoapp.domain.exception

data class CreateUpdateException(val process: String):RuntimeException(
    "$process : Typing Error"
)
