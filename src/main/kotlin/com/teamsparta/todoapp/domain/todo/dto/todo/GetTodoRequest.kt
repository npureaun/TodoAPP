package com.teamsparta.todoapp.domain.todo.dto.todo

import io.swagger.v3.oas.annotations.media.Schema


data class GetTodoRequest(
    @Schema(example = "null", required = false)
    val title:String?=null,
    @Schema( example = "null", required = false)
    val tag:String?=null
){
    companion object{
        fun convertNullString(request: GetTodoRequest):GetTodoRequest{
            return GetTodoRequest(
                title = if(request.title=="null") null else request.title,
                tag=if(request.tag=="null") null else request.tag
            )
        }
    }
}