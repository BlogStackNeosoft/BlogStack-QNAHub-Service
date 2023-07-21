package com.blogstack.controller;

import com.blogstack.beans.requests.CommentMasterRequestBean;
import com.blogstack.service.IBlogStackCommentMasterService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@Validated
@RequestMapping("/comment")
@CrossOrigin(origins = "*")
public class BlogStackCommentMasterController {
    @Autowired
    private IBlogStackCommentMasterService blogStackCommentMasterService;

    @PostMapping
    public ResponseEntity<?> addComment(@Valid @RequestBody CommentMasterRequestBean commentMasterRequestBean) {
        return ResponseEntity.ok(this.blogStackCommentMasterService.addComment(commentMasterRequestBean));
    }

    @GetMapping
    public ResponseEntity<?> fetchAllComment(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "2147483647") Integer size) {
        return ResponseEntity.ok(this.blogStackCommentMasterService.fetchAllComment(page, size));
    }

    @GetMapping("/{comment_id}")
    public ResponseEntity<?> fetchCommentById(@PathVariable(value = "comment_id") @NotBlank(message = "Question Id can not be empty") String commentId) {
        return ResponseEntity.ok (this.blogStackCommentMasterService.fetchCommentById(commentId));
    }

    @PutMapping
    public ResponseEntity<?> updateComment(@Valid @RequestBody CommentMasterRequestBean commentMasterRequestBean) {
        return ResponseEntity.ok(this.blogStackCommentMasterService.updateComment(commentMasterRequestBean));
    }

    @DeleteMapping("/{comment_id}")
    public ResponseEntity<?> deleteComment(@PathVariable(value = "comment_id") @NotBlank(message = "Comment Id can not be empty") String commentId) {
        return ResponseEntity.ok( this.blogStackCommentMasterService.deleteComment(commentId));
    }

}
