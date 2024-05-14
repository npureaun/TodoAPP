package com.teamsparta.todoapp.domain.comment.controller

import com.teamsparta.todoapp.domain.comment.dto.CommentResponse
import com.teamsparta.todoapp.domain.comment.service.CommentService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/comment")
@RestController
class CommentController(private val commentService: CommentService) {
    @GetMapping
    fun getCommentList()
            : ResponseEntity<List<CommentResponse>>
            = ResponseEntity
        .status(HttpStatus.OK)
        .body(commentService.getAllComment())
}