package com.teamsparta.todoapp.domain.todo.repository.todo

import com.querydsl.core.BooleanBuilder
import com.teamsparta.todoapp.domain.todo.model.QTodo
import com.teamsparta.todoapp.domain.todo.model.Todo
import com.teamsparta.todoapp.querydsl.QueryDslSupport
import org.springframework.stereotype.Repository
import com.teamsparta.todoapp.domain.todo.model.QComment.comment1

@Repository
class TodoRepositoryImpl
    : QueryDslSupport(), CustomTodoRepository {

    private val todo = QTodo.todo
    override fun searchTodoListByTitle(title: String?): List<Todo> {
        val whereClause = BooleanBuilder()
        title?.let{whereClause.and(todo.title.containsIgnoreCase(title))}

        val result= queryFactory
            .select(todo)
            .from(todo)
            .where(whereClause)
            .leftJoin(todo.comments,comment1)
            .fetchJoin()
            .fetch()
        return result
    }
}
