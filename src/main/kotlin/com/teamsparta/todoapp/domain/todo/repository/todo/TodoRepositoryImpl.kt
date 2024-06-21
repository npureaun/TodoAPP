package com.teamsparta.todoapp.domain.todo.repository.todo

import com.querydsl.core.BooleanBuilder
import com.querydsl.core.types.Expression
import com.querydsl.core.types.Order
import com.querydsl.core.types.OrderSpecifier
import com.querydsl.core.types.dsl.EntityPathBase
import com.querydsl.core.types.dsl.PathBuilder
import org.springframework.data.domain.Pageable
import com.teamsparta.todoapp.domain.todo.model.QTodo
import com.teamsparta.todoapp.domain.todo.model.Todo
import com.teamsparta.todoapp.querydsl.QueryDslSupport
import org.springframework.stereotype.Repository
import com.teamsparta.todoapp.domain.todo.model.QComment.comment1

@Repository
class TodoRepositoryImpl
    : QueryDslSupport(), TodoRepositoryCustom {

    private val todo = QTodo.todo
    override fun searchTodoListByTitle(pageable: Pageable,title: String?): List<Todo> {
        val whereClause = BooleanBuilder()
        title?.let{whereClause.and(todo.title.containsIgnoreCase(title))}

        val result= queryFactory
            .select(todo)
            .from(todo)
            .where(whereClause)
            .leftJoin(todo.comments,comment1)
            .offset(pageable.offset)
            .limit(pageable.pageSize.toLong())
            .orderBy(*getOrderSpecifier(pageable, todo))
            .fetchJoin()
            .fetch()
        return result
    }

    private fun getOrderSpecifier(
        pageable: Pageable,
        path: EntityPathBase<*>)
            : Array<OrderSpecifier<*>> {
        val pathBuilder = PathBuilder(path.type, path.metadata)

        return pageable.sort.toList().map { order ->
            OrderSpecifier(
                if (order.direction.isAscending) Order.ASC
                else Order.DESC,
                pathBuilder.get(order.property)
                        as Expression<Comparable<*>>
            )
        }.toTypedArray()
    }
}
