package com.teamsparta.todoapp.domain.todo.repository.comment

import com.teamsparta.todoapp.domain.todo.model.Comment
import com.teamsparta.todoapp.domain.todo.model.QComment
import com.teamsparta.todoapp.querydsl.QueryDslSupport

class CommentRepositoryImpl:QueryDslSupport()
    , CommentRepositoryCustom {
    private val comment=QComment.comment1
}