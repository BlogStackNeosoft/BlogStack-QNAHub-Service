package com.blogstack.controller;

import com.blogstack.beans.requests.QuestionMasterRequestBean;
import com.blogstack.beans.responses.ServiceResponseBean;
import com.blogstack.service.IBlogStackQuestionMasterService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.Set;

@RestController
@Validated
@RequestMapping("${qnahub-service-version}/question")
@AllArgsConstructor
@CrossOrigin("*")
public class BlogStackQuestionMasterController {

    private final IBlogStackQuestionMasterService blogStackQuestionMasterService;

    @PostMapping("/")
    public ResponseEntity<?> addQuestion(@Valid @RequestBody QuestionMasterRequestBean questionMasterRequestBean){
        return ResponseEntity.ok(this.blogStackQuestionMasterService.addQuestion(questionMasterRequestBean));
    }

    @GetMapping("/")
    public ResponseEntity<?> fetchAllQuestion(@RequestParam(defaultValue = "0") Integer page,
                                              @RequestParam(defaultValue = "10") Integer size){
        return ResponseEntity.ok(this.blogStackQuestionMasterService.fetchAllQuestion(page, size));
    }

    @GetMapping("/{question_id}")
    public ResponseEntity<?> fetchQuestionById(@PathVariable(value = "question_id") @NotBlank(message = "Question Id can not be empty.") String questionId){
        return ResponseEntity.ok(this.blogStackQuestionMasterService.fetchQuestionById(questionId));
    }

    @PutMapping("/")
    public ResponseEntity<?> updateQuestion(@Valid @RequestBody QuestionMasterRequestBean questionMasterRequestBean){
        return ResponseEntity.ok(this.blogStackQuestionMasterService.updateQuestion(questionMasterRequestBean));
    }

    @DeleteMapping("/{question_id}")
    public ResponseEntity<?> deleteQuestion(@PathVariable(value = "question_id") @NotBlank(message = "Question Id can not be empty.") String questionId){
        return ResponseEntity.ok(this.blogStackQuestionMasterService.deleteQuestion(questionId));
    }

    @GetMapping("/all-questions")
    public ResponseEntity<?> fetchAllQuestionsBySetIds(@RequestParam("ids") Set<String> ids){
        return ResponseEntity.ok(this.blogStackQuestionMasterService.fetchAllQuestionsByQuestionIds(ids));
    }

    @GetMapping("/all-questions/{user_id}")
    public ResponseEntity<?> fetchAllQuestionsByUserId(@PathVariable(value = "user_id") @NotBlank(message = "user id can not be blank") String userId){
        return ResponseEntity.ok(this.blogStackQuestionMasterService.fetchAllQuestionsByUserId(userId));
    }
}