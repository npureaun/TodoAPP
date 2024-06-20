package com.teamsparta.todoapp.domain.todo.repository.todo

import com.teamsparta.todoapp.domain.todo.model.Todo
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Slice
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface TodoRepository:JpaRepository<Todo, Long>, TodoRepositoryCustom {
    @EntityGraph(attributePaths = ["comments"])
    @Query("SELECT tl FROM Todo tl")
    fun findAllWithSort(pageable: Pageable): Slice<Todo>

    @EntityGraph(attributePaths = ["comments"])
    @Query("SELECT tl FROM Todo tl WHERE tl.nickname = :nickname")
    fun findWriterWithSort(@Param("nickname") nickname: String
                           , pageable: Pageable): Slice<Todo>

    @Query("SELECT CASE WHEN tl.userEmail = :user_email THEN true ELSE false END FROM Todo tl WHERE tl.id = :todo_id")
    fun matchingByUserIdOrBool(@Param("todo_id") todoId: Long, @Param("user_email") userEmail:String)
    : Boolean
}