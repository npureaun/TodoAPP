package com.teamsparta.todoapp.domain.todo.repository

import com.teamsparta.todoapp.domain.todo.model.Todo
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Slice
import org.springframework.data.domain.Sort
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface TodoRepository:JpaRepository<Todo, Long> {
    @EntityGraph(attributePaths = ["comments"])
    @Query("SELECT tl FROM Todo tl")
    fun findAllWithSort(pageable: Pageable): Slice<Todo>

    @EntityGraph(attributePaths = ["comments"])
    @Query("SELECT tl FROM Todo tl WHERE tl.writer = :written")
    fun findWriterWithSort(@Param("written") writer: String
                           , pageable: Pageable): Slice<Todo>
}