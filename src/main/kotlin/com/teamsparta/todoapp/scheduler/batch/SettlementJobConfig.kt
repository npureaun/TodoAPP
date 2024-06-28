package com.teamsparta.todoapp.scheduler.batch

import com.teamsparta.todoapp.domain.todo.repository.todo.TodoRepository
import org.springframework.batch.core.Step
import org.springframework.batch.core.Job
import org.springframework.batch.core.job.builder.JobBuilder
import org.springframework.batch.core.repository.JobRepository
import org.springframework.batch.core.step.builder.StepBuilder
import org.springframework.batch.core.step.tasklet.Tasklet
import org.springframework.batch.repeat.RepeatStatus
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.transaction.PlatformTransactionManager

@Configuration
class SettlementJobConfig (
    private val transactionManager: PlatformTransactionManager,
){
    @Bean
    fun settlementJob(jobRepository: JobRepository,createStep:Step): Job {
        return JobBuilder("settlementJob",jobRepository)
            .start(createStep)
            .build()
    }

    @Bean
    fun createStep(
        jobRepository: JobRepository,
        settlementTasklet: Tasklet,
    ):Step{
        return StepBuilder("createStep",jobRepository)
            .tasklet(settlementTasklet, transactionManager)
            .build()
    }
}