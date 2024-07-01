package com.teamsparta.todoapp.scheduler

import org.springframework.batch.core.*
import org.springframework.batch.core.launch.JobLauncher
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
@EnableScheduling
class ScheduledDeletionTasks(
    private val jobLauncher: JobLauncher,
    private val settlementJob: Job
) {

    @Scheduled(cron = "0 */10 * * * *") // 10분마다 실행
    fun performJob() {
        try {
            val jobParameters: JobParameters = JobParametersBuilder()
                .addLong("startAt", System.currentTimeMillis())
                .toJobParameters()
            jobLauncher.run(settlementJob, jobParameters)
        } catch (e: JobExecutionException) {
            e.printStackTrace()
        }
    }
}