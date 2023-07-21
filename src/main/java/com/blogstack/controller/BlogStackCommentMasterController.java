package com.blogstack.controller;

import com.blogstack.beans.requests.CommentMasterRequestBean;
import com.blogstack.service.IBlogStackCommentMasterService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@Validated
@RequestMapping("/comment")
@CrossOrigin(origins = "*")
@AllArgsConstructor
public class BlogStackCommentMasterController {

    private final IBlogStackCommentMasterService blogStackCommentMasterService;

    @PostMapping("/answer/{answerId}")
    public ResponseEntity<?> addComment(@PathVariable(value = "answerId") String answerId, @Valid @RequestBody CommentMasterRequestBean commentMasterRequestBean) {
        return ResponseEntity.ok(this.blogStackCommentMasterService.addComment(answerId, commentMasterRequestBean));
    }

    @GetMapping
    public ResponseEntity<?> fetchAllComment(@RequestParam(defaultValue = "0") Integer page,
                                             @RequestParam(defaultValue = "10") Integer size) {
        return ResponseEntity.ok(this.blogStackCommentMasterService.fetchAllComment(page, size));
    }

    @GetMapping("/answer/{answerId}")
    public ResponseEntity<?> fetchAllCommentsByAnswerId(@PathVariable(value = "answerId") String answerId){
        return ResponseEntity.ok(this.blogStackCommentMasterService.fetchAllCommentsByAnswerId(answerId));
    }

    @GetMapping("/{commentId}")
    public ResponseEntity<?> fetchCommentById(@PathVariable(value = "commentId") @NotBlank(message = "Question Id can not be empty") String commentId) {
        return ResponseEntity.ok(this.blogStackCommentMasterService.fetchCommentById(commentId));
    }

    @PutMapping("/")
    public ResponseEntity<?> updateComment(@Valid @RequestBody CommentMasterRequestBean commentMasterRequestBean) {
        return ResponseEntity.ok(this.blogStackCommentMasterService.updateComment(commentMasterRequestBean));
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<?> deleteComment(@PathVariable(value = "commentId") @NotBlank(message = "Comment Id can not be empty") String commentId) {
        return ResponseEntity.ok(this.blogStackCommentMasterService.deleteComment(commentId));
    }

    @DeleteMapping("/answer/{answerId}")
    public ResponseEntity<?> deleteAllCommentByAnswerId(@PathVariable(value = "answerId") String answerId) {
        return ResponseEntity.ok(this.blogStackCommentMasterService.deleteAllCommentByAnswerId(answerId));
    }
}