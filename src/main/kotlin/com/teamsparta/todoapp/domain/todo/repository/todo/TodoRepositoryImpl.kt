package com.teamsparta.todoapp.domain.todo.repository.todo

import com.teamsparta.todoapp.domain.todo.model.QTodo
import com.teamsparta.todoapp.domain.todo.model.Todo
import com.teamsparta.todoapp.querydsl.QueryDslSupport
import org.springframework.stereotype.Repository

@Repository
class TodoRepositoryImpl: QueryDslSupport(), CustomTodoRepository {

    private val todo = QTodo.todo
    override fun searchCourseListByTitle(title: String): List<Todo> {
        return queryFactory.selectFrom(todo)
            .where(todo.title.containsIgnoreCase(title))
            .fetch()
    }

}