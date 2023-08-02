package com.blogstack.controller;

import com.blogstack.beans.requests.BlogMasterRequestBean;
import com.blogstack.beans.requests.QuestionMasterRequestBean;
import com.blogstack.beans.responses.ServiceResponseBean;
import com.blogstack.service.IBlogStackBlogsService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("${qnahub-service-version}/blog")
public class BlogStackBlogMasterController {

    @Autowired
    private IBlogStackBlogsService blogStackBlogsService;

    @PostMapping("/")
    public ResponseEntity<Optional<ServiceResponseBean>> addQuestion(@Valid @RequestBody BlogMasterRequestBean blogMasterRequestBean){
        return ResponseEntity.ok(this.blogStackBlogsService.addBlog(blogMasterRequestBean));
    }

    @GetMapping("/")
    public ResponseEntity<Optional<ServiceResponseBean>> fetchAllQuestion(@RequestParam(defaultValue = "0") Integer page,
                                              @RequestParam(defaultValue = "10") Integer size){
        return ResponseEntity.ok(this.blogStackBlogsService.fetchAllBlogs(page, size));
    }
    @GetMapping("/{blog_id}")
    public ResponseEntity<Optional<ServiceResponseBean>> fetchQuestionById(@PathVariable(value = "blog_id") @NotBlank(message = "Question Id can not be empty.") String blogId){
        return ResponseEntity.ok(this.blogStackBlogsService.fetchBlogById(blogId));
    }

    @PutMapping("/")
    public ResponseEntity<Optional<ServiceResponseBean>> updateQuestion(@Valid @RequestBody BlogMasterRequestBean blogMasterRequestBean){
        return ResponseEntity.ok(this.blogStackBlogsService.updateBlog(blogMasterRequestBean));
    }

    @DeleteMapping("/{blog_id}")
    public ResponseEntity<Optional<ServiceResponseBean>> deleteQuestion(@PathVariable(value = "blog_id") @NotBlank(message = "Question Id can not be empty.") String blogId){
        return ResponseEntity.ok(this.blogStackBlogsService.deleteBlog(blogId));
    }
}
