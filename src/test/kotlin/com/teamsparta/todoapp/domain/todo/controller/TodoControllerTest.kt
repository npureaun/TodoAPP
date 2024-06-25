package com.teamsparta.todoapp.domain.todo.controller

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import com.teamsparta.todoapp.domain.todo.dto.todo.TodoResponse
import com.teamsparta.todoapp.domain.todo.service.TodoService
import com.teamsparta.todoapp.infra.security.jwt.JwtUtil
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.extensions.spring.SpringExtension
import io.kotest.matchers.shouldBe
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockKExtension::class)
class TodoControllerTest @Autowired constructor(
    private val mockMvc: MockMvc,
    private val jwtUtil: JwtUtil,
):DescribeSpec({
    extensions(SpringExtension)

    afterContainer { clearAllMocks() }

    val todoService= mockk<TodoService>()

    describe("GET /todos/{todoId}") {
        context("존재하는 ID를 요청할 때"){
            it("200 status code 를 응답한다"){
                val todoId=120L

                every { todoService.getTodoById(any()) } returns TodoResponse(
                    id= todoId,
                    title = "title test",
                    description= "description test",
                    created= "time test",
                    nickname= "nickname test",
                    success= false,
                    commentList= emptyList(),
                )

                val jwtToken = jwtUtil.generateAccessToken(
                    subject = "1",
                    role = "DEVELOP"
                )

                val result = mockMvc.perform(
                    get("/todos/$todoId")
                        .header("Authorization", "Bearer $jwtToken")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                ).andReturn()

                result.response.status shouldBe 200

                val responseDto = jacksonObjectMapper().readValue(
                    result.response.contentAsString,
                    TodoResponse::class.java
                )
                responseDto.id shouldBe todoId
            }
        }

    }
})