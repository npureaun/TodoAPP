package com.teamsparta.todoapp.domain.todo.controller

import com.teamsparta.todoapp.domain.todo.dto.comment.CommentResponse
import com.teamsparta.todoapp.domain.todo.dto.comment.CreateCommentRequest
import com.teamsparta.todoapp.domain.todo.dto.comment.UpdateCommentRequest
import com.teamsparta.todoapp.domain.todo.service.CommentService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RequestMapping("/todos/{todoId}/comments")
@RestController
class CommentController(private val commentService: CommentService) {
    @PostMapping
    @PreAuthorize("hasRole('STANDARD') or hasRole('DEVELOP')")
    fun createComment(
        @PathVariable todoId: Long,
        @RequestBody createRequest: CreateCommentRequest)
            : ResponseEntity<CommentResponse> {
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(commentService.createComment(todoId, createRequest))
    }

    @PutMapping("/{commentId}")
    @PreAuthorize("hasRole('STANDARD') or hasRole('DEVELOP')")
    fun updateComment(
        @PathVariable todoId: Long,
        @PathVariable commentId: Long,
        @RequestBody updateCommentRequest: UpdateCommentRequest)
            : ResponseEntity<CommentResponse> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(commentService.updateComment(commentId, updateCommentRequest))
    }


    @DeleteMapping("/{commentId}")
    @PreAuthorize("hasRole('STANDARD') or hasRole('DEVELOP')")
    fun deleteComment(
        @PathVariable todoId: Long,
        @PathVariable commentId: Long,)
            : ResponseEntity<Unit> {
        return ResponseEntity
            .status(HttpStatus.NO_CONTENT)
            .body(commentService.deleteComment(commentId))
    }
}





