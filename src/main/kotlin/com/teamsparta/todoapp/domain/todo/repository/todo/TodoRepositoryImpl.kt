package com.teamsparta.todoapp.domain.todo.repository.todo

import com.teamsparta.todoapp.domain.todo.model.QTodo
import com.teamsparta.todoapp.domain.todo.model.Todo
import com.teamsparta.todoapp.querydsl.QueryDslSupport
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Slice
import org.springframework.data.web.config.EnableSpringDataWebSupport
import org.springframework.stereotype.Repository

@Repository
class TodoRepositoryImpl(private val querydslActivator: EnableSpringDataWebSupport.QuerydslActivator)
    : QueryDslSupport(), CustomTodoRepository {

    private val todo = QTodo.todo
    override fun searchTodoListByTitle(title: String): List<Todo> {
        return queryFactory.selectFrom(todo)
            .where(todo.title.containsIgnoreCase(title))
            .fetch()
    }

//    override fun findTodoListByNickname(nickname: String, pageable: Pageable)
//    : Slice<Todo> {
//        val query = queryFactory.selectFrom(todo)
//            .apply {
//                if (nickname.isNotBlank()) {
//                    where(todo.nickname.eq(nickname))
//                }
//            }
//        return queryFactory.applyPagination(pageable, query).fetchResults()
//    }//모르겠다..이런.
}
