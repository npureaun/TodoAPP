package com.teamsparta.todoapp.domain.todo.repository

import com.teamsparta.todoapp.domain.todo.model.Todo
import org.springframework.data.domain.Sort
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface TodoRepository:JpaRepository<Todo, Long> {
    @Query("SELECT tl FROM Todo tl")
    fun findAllWithSort(sort: Sort): List<Todo>

    @Query("SELECT tl FROM Todo tl WHERE tl.writer = :written")
    fun findWriterWithSort(sort: Sort,@Param("written")writer:String)
    : List<Todo>
}