package com.teamsparta.todoapp.infra.aop

import org.aspectj.lang.JoinPoint
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.util.StopWatch

@Component
@Aspect
class StopWatchAspect{
    private val logger = LoggerFactory.getLogger("Execution Time Logger")

    @Around("@annotation(com.teamsparta.todoapp.infra.aop.Stopwatch)")
    fun run(joinPoint: ProceedingJoinPoint):Any {
        val stopWatch = StopWatch()
        stopWatch.start()
        val result= joinPoint.proceed()
        stopWatch.stop()

        logger.info("methodName : ${joinPoint.signature.name}" +
                " | methodArgument : ${joinPoint.args.joinToString(", ")}"+
                " | Execution Time: ${stopWatch.totalTimeMillis}ms")
        return result
    }
}
