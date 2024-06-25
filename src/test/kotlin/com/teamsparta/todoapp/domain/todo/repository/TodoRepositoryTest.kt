package com.teamsparta.todoapp.domain.todo.repository

import org.junit.jupiter.api.Test
import com.teamsparta.todoapp.domain.todo.model.Todo
import com.teamsparta.todoapp.domain.todo.repository.todo.TodoRepository
import com.teamsparta.todoapp.infra.QueryDslConfig
import io.kotest.matchers.shouldBe
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.context.annotation.Import
import org.springframework.data.domain.PageRequest
import org.springframework.test.context.ActiveProfiles


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(value = [QueryDslConfig::class])
@ActiveProfiles("test")
class TodoRepositoryTest @Autowired constructor(
    private val todoRepository: TodoRepository,
) {

    @Test
    fun `SearchType 이 NONE 일 경우 전체 데이터 조회되는지 확인`() {
        // GIVEN
        todoRepository.saveAllAndFlush(DEFAULT_MEMBER_LIST)
        // WHEN
        val pageable= PageRequest.of(0, 10)
        val result=todoRepository.searchTodoListByTitle(pageable,"","")
        // THEN
        result.content.size shouldBe 10
    }

    @Test
    fun `SearchType 이 title 일 경우 해당 데이터 조회되는지 확인`() {
        // GIVEN
        todoRepository.saveAllAndFlush(DEFAULT_MEMBER_LIST)
        // WHEN
        val pageable= PageRequest.of(0, 10)
        val result=todoRepository.searchTodoListByTitle(pageable,"test title3","")
        // THEN
        result.content.first().title shouldBe "test title3"
    }

    @Test
    fun `SearchType 이 tag 일 경우 해당 데이터 조회되는지 확인`() {
        // GIVEN
        todoRepository.saveAllAndFlush(DEFAULT_MEMBER_LIST)
        // WHEN
        val pageable= PageRequest.of(0, 10)
        val result=todoRepository.searchTodoListByTitle(pageable,"","description")
        // THEN
        result.content.size shouldBe DEFAULT_MEMBER_LIST.size
    }


    companion object {
        private val DEFAULT_MEMBER_LIST = listOf(
            Todo(title = "test title1", description = "test description1", nickname = "test nickname", userEmail = "test user email"),
            Todo(title = "test title2", description = "test description2", nickname = "test nickname", userEmail = "test user email"),
            Todo(title = "test title3", description = "test description3", nickname = "test nickname", userEmail = "test user email"),
            Todo(title = "test title4", description = "test description4", nickname = "test nickname", userEmail = "test user email"),
            Todo(title = "test title5", description = "test description5", nickname = "test nickname", userEmail = "test user email"),
            Todo(title = "test title6", description = "test description6", nickname = "test nickname", userEmail = "test user email"),
            Todo(title = "test title7", description = "test description7", nickname = "test nickname", userEmail = "test user email"),
            Todo(title = "test title8", description = "test description8", nickname = "test nickname", userEmail = "test user email"),
            Todo(title = "test title9", description = "test description9", nickname = "test nickname", userEmail = "test user email"),
            Todo(title = "test title10", description = "test description10", nickname = "test nickname", userEmail = "test user email")
        )
    }
}