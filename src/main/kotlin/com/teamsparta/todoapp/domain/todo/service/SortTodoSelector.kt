package com.teamsparta.todoapp.domain.todo.service

import com.teamsparta.todoapp.domain.todo.model.Todo
import org.springframework.data.domain.Sort

enum class SortTodoSelector(
    val sort: Sort
) {
    SUCCESS_ASC_DATE_ASC(
        Sort.by(
            Sort.Order.asc("success"),
            Sort.Order.asc("created")
        )),
    SUCCESS_DESC_DATE_ASC(
        Sort.by(
            Sort.Order.desc("success"),
            Sort.Order.asc("created")
        )),
    SUCCESS_ASC_DATE_DESC(
        Sort.by(
            Sort.Order.asc("success"),
            Sort.Order.desc("created")
        )),
    SUCCESS_DESC_DATE_DESC(
        Sort.by(
            Sort.Order.desc("success"),
            Sort.Order.desc("created")
        ));
}

