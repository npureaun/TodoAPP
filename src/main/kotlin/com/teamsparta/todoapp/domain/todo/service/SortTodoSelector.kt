package com.teamsparta.todoapp.domain.todo.service

import com.teamsparta.todoapp.domain.todo.model.Todo
import org.springframework.data.domain.Sort

enum class SortTodoSelector(
    val comparator: Comparator<Todo>
) {
    SUCCESS_ASC_DATE_ASC(
        compareBy<Todo>{it.success}.thenBy { it.created }
    ),
    SUCCESS_DESC_DATE_ASC(
        compareBy<Todo>{it.success}.reversed().thenBy { it.created }
    ),
    SUCCESS_ASC_DATE_DESC(
        compareBy<Todo>{it.success}.thenBy { it.created }.reversed()
    ),
    SUCCESS_DESC_DATE_DESC(
        compareBy<Todo>{it.success}.reversed().thenBy { it.created }.reversed()
    );
}

