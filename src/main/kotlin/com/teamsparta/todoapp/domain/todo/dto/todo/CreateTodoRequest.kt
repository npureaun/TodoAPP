package com.teamsparta.todoapp.domain.todo.dto.todo

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class CreateTodoRequest(
    @field:NotBlank
    @field:Size(max = 200)
    val title:String,

    @field:NotBlank
    @field:Size(max = 1000)
    val description:String,

    val writer:String,
)
