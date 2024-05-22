package com.teamsparta.todoapp.domain.exception

import com.teamsparta.todoapp.domain.exception.dto.ErrorResponse
import jakarta.persistence.EntityNotFoundException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {
    @ExceptionHandler(ModelNotFoundException::class)
    fun handleModelNotFoundException(e: ModelNotFoundException): ResponseEntity<ErrorResponse> {
        return ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(ErrorResponse(message = e.message, errorCode = null))
    }

    @ExceptionHandler(PasswordUnMatchingException::class)
    fun handleIllegalStateException(e: PasswordUnMatchingException): ResponseEntity<ErrorResponse> {
        return ResponseEntity
            .status(HttpStatus.CONFLICT)
            .body(ErrorResponse(e.message, errorCode = null))
    }

    @ExceptionHandler(CreateUpdateException::class)
    fun handleTypingStateException(e: CreateUpdateException): ResponseEntity<ErrorResponse> {
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(ErrorResponse(e.message, errorCode = null))
    }

    @ExceptionHandler(EntityNotFoundException::class)
    fun handleTypingStateException(e: EntityNotFoundException): ResponseEntity<ErrorResponse> {
        return ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(ErrorResponse(e.message, errorCode = null))
    }
}