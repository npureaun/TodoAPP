package com.teamsparta.todoapp

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.EnableAspectJAutoProxy

@EnableAspectJAutoProxy
@SpringBootApplication
class TodoAppApplication

fun main(args: Array<String>) {
    runApplication<TodoAppApplication>(*args)
}
