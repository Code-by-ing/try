package com.taskmanagement.controller;

import com.taskmanagement.common.Result;
import com.taskmanagement.entity.Comment;
import com.taskmanagement.service.CommentService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping("/tasks/{taskId}/comments")
    public Result<List<Comment>> list(@PathVariable Long taskId) {
        return Result.success(commentService.listCommentsByTaskId(taskId));
    }

    @PostMapping("/tasks/{taskId}/comments")
    public Result<Comment> add(@PathVariable Long taskId,
                               @RequestBody Comment comment,
                               HttpServletRequest request) {
        comment.setTaskId(taskId);
        Long memberId = (Long) request.getAttribute("memberId");
        comment.setMemberId(memberId);
        return Result.success(commentService.addComment(comment));
    }

    @DeleteMapping("/comments/{id}")
    public Result<Void> delete(@PathVariable Long id, HttpServletRequest request) {
        Long memberId = (Long) request.getAttribute("memberId");
        commentService.deleteComment(id, memberId);
        return Result.success();
    }
}
