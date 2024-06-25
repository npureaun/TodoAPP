package com.teamsparta.todoapp.domain.todo.service

import com.teamsparta.todoapp.domain.todo.repository.todo.TodoRepository
import com.teamsparta.todoapp.domain.user.service.UserService
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.extensions.spring.SpringExtension
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import jakarta.persistence.EntityNotFoundException
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.repository.findByIdOrNull


@SpringBootTest
@ExtendWith(MockKExtension::class)
class TodoServiceTest : BehaviorSpec({
    extension(SpringExtension)
    afterContainer {
        clearAllMocks()
    }
    val userService = mockk<UserService>()
    val todoRepository = mockk<TodoRepository>()
    val todoService = TodoService(userService = userService, todoRepository = todoRepository)

    Given("Todo 목록이 존재하지 않을때") {

        When("특정 Todo 을 요청하면") {

            Then("EntityNotFoundException 이 발생해야 한다.") {
                val todoId = 1L
                every { todoRepository.findByIdOrNull(todoId) } returns null

                shouldThrow<EntityNotFoundException> {
                    println(todoService.getTodoById(todoId))
                }

            }

        }
    }
})