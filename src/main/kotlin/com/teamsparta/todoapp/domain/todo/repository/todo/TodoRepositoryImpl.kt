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
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Slice
import org.springframework.data.domain.SliceImpl

@Repository
class TodoRepositoryImpl
    : QueryDslSupport(), TodoRepositoryCustom {

    private val todo = QTodo.todo
    override fun searchTodoListByTitle(pageable: Pageable,title: String?, tag:String?): Page<Todo> {

        val query= queryFactory
            .select(todo)
            .from(todo)
            .where(titleLike(title),tagLike(tag))
            .offset(pageable.offset)
            .limit(pageable.pageSize.toLong())
            .leftJoin(todo.comments,comment1)
            .orderBy(*getOrderSpecifier(pageable, todo))
            .fetchJoin()
            .fetch()

        return PageImpl(query)
    }

    private fun titleLike(title: String?):BooleanBuilder?
    = title?.let{BooleanBuilder().and(todo.title.containsIgnoreCase(title))}


    private fun tagLike(tag:String?):BooleanBuilder?
    = tag?.let { BooleanBuilder().and(todo.description.containsIgnoreCase(tag)) }

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

    private fun deleteSearch():BooleanBuilder?
            = BooleanBuilder().and(todo.isDelete.isTrue)

    override fun deleteByIsDelete() {
        queryFactory
            .delete(todo)
            .where(deleteSearch())
            .execute()
    }
}
