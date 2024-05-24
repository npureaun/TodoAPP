package com.teamsparta.todoapp.domain.todo.controller

import com.teamsparta.todoapp.domain.security.jwt.JwtUtil
import com.teamsparta.todoapp.domain.todo.dto.comment.CommentResponse
import com.teamsparta.todoapp.domain.todo.dto.comment.CreateCommentRequest
import com.teamsparta.todoapp.domain.todo.dto.comment.DeleteCommentRequest
import com.teamsparta.todoapp.domain.todo.dto.comment.UpdateCommentRequest
import com.teamsparta.todoapp.domain.todo.service.CommentService
import org.hibernate.service.spi.ServiceException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RequestMapping("/todos/{todoId}/comments")
@RestController
class CommentController(private val commentService: CommentService) {
    @PostMapping
    fun createComment(
        @PathVariable todoId: Long,
        @RequestBody createRequest: CreateCommentRequest)
            : ResponseEntity<CommentResponse> {
        if(JwtUtil.getUserIdFromToken(createRequest.token!!)==null)
            throw ServiceException("not Token")
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(commentService.createComment(todoId, createRequest))
    }

    @PutMapping("/{commentId}")
    fun updateComment(
        @PathVariable todoId: Long,
        @PathVariable commentId: Long,
        @RequestBody updateCommentRequest: UpdateCommentRequest)
            : ResponseEntity<CommentResponse> {
        if(JwtUtil.getUserIdFromToken(updateCommentRequest.token!!)==null)
            throw ServiceException("not Token")
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(commentService.updateComment(commentId, updateCommentRequest))
    }


    @DeleteMapping("/{commentId}")
    fun deleteComment(
        @PathVariable todoId: Long,
        @PathVariable commentId: Long,
        @RequestBody deleteCommentRequest: DeleteCommentRequest)
            : ResponseEntity<Unit> {
        if(JwtUtil.getUserIdFromToken(deleteCommentRequest.token!!)==null)
            throw ServiceException("not Token")
        return ResponseEntity
            .status(HttpStatus.NO_CONTENT)
            .body(commentService.deleteComment(commentId, deleteCommentRequest))
    }
}





