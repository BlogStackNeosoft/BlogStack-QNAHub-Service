package com.blogstack.controller;

import com.blogstack.beans.requests.BlogMasterRequestBean;
import com.blogstack.beans.requests.QuestionMasterRequestBean;
import com.blogstack.service.IBlogStackBlogsService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${qnahub-service-version}/blog")
public class BlogStackBlogMasterController {

    @Autowired
    private IBlogStackBlogsService blogStackBlogsService;

    @PostMapping("/")
    public ResponseEntity<?> addQuestion(@Valid @RequestBody BlogMasterRequestBean blogMasterRequestBean){
        return ResponseEntity.ok(this.blogStackBlogsService.addBlog(blogMasterRequestBean));
    }
}
