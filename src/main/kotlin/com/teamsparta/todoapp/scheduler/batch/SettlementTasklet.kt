package com.teamsparta.todoapp.scheduler.batch

import com.teamsparta.todoapp.domain.todo.repository.todo.TodoRepository
import org.springframework.batch.core.StepContribution
import org.springframework.batch.core.scope.context.ChunkContext
import org.springframework.batch.core.step.tasklet.Tasklet
import org.springframework.batch.repeat.RepeatStatus
import org.springframework.stereotype.Component

@Component
class SettlementTasklet(
    private val todoRepository: TodoRepository,
):Tasklet {
    override fun execute(contribution: StepContribution, chunkContext: ChunkContext): RepeatStatus? {
        todoRepository.deleteByIsDelete()
        return RepeatStatus.FINISHED
    }
}