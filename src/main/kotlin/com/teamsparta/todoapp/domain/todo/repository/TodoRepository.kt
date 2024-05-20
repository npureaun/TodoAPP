package com.teamsparta.todoapp.domain.todo.repository

import com.teamsparta.todoapp.domain.todo.model.Todo
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface TodoRepository:JpaRepository<Todo, Long> {
    fun findAllByOrderBySuccessAscCreatedDateAsc(): List<Todo>
    fun findAllByOrderBySuccessDescCreatedDateAsc(): List<Todo>
    fun findAllByOrderBySuccessAscCreatedDateDesc(): List<Todo>
    fun findAllByOrderBySuccessDescCreatedDateDesc(): List<Todo>

//    @Query("select tl from todo_list tl order by success:first, created_date:second")
//    fun searchByTitle(@Param("title") keyword: String): List<Todo>
}