package com.blogstack.controller;

import com.blogstack.beans.requests.BlogCommentMasterRequestBean;
import com.blogstack.service.IBlogStackBlogCommentMasterService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("${qnahub-service-version}/blog-comments")
public class BlogStackBlogCommentMasterController {

    @Autowired
    private IBlogStackBlogCommentMasterService blogStackBlogCommentMasterService;

    @PostMapping("/{blog-id}")
    public ResponseEntity<?> addComment(@PathVariable(value = "blog-id") String blogId, @Valid @RequestBody BlogCommentMasterRequestBean blogCommentMasterRequestBean) {
        return ResponseEntity.ok(this.blogStackBlogCommentMasterService.addBlogComment(blogId, blogCommentMasterRequestBean));
    }



}
