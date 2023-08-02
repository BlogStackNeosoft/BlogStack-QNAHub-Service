package com.blogstack.controller;

import com.blogstack.beans.requests.CategoryMasterRequestBean;
import com.blogstack.beans.responses.ServiceResponseBean;
import com.blogstack.service.IBlogStackCategoryMasterService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Validated
@RequestMapping("${qnahub-service-version}/category")
@AllArgsConstructor
@CrossOrigin("*")
public class BlogStackCategoryMasterController {
    @Autowired

    private IBlogStackCategoryMasterService blogStackCategoryMasterService;

    @PostMapping("/")
    public ResponseEntity<ServiceResponseBean> addCategory(@Valid @RequestBody CategoryMasterRequestBean categoryMasterRequestBean) {
        return ResponseEntity.ok(this.blogStackCategoryMasterService.addCategory(categoryMasterRequestBean).getBody());

    }

    @GetMapping("/")
    public ResponseEntity<ServiceResponseBean> fetchAllCategory(@RequestParam(defaultValue = "0") Integer page,
                                                                @RequestParam(defaultValue = "10") Integer size) {
        return ResponseEntity.ok(this.blogStackCategoryMasterService.fetchAllCategories(page, size).getBody());
    }

    @GetMapping("/{category_id}")
    public ResponseEntity<ServiceResponseBean> fetchCategoryById(@PathVariable(value = "category_id") @NotBlank(message = "category Id can not be empty.") String categoryId) {
        return ResponseEntity.ok(this.blogStackCategoryMasterService.fetchCategoryById(categoryId)).getBody();
    }

    @PutMapping("/{category_id}")
    public ResponseEntity<ServiceResponseBean> updateCategory(@Valid @RequestBody CategoryMasterRequestBean categoryMasterRequestBean) {
        return ResponseEntity.ok(this.blogStackCategoryMasterService.updateCategory(categoryMasterRequestBean)).getBody();
    }

    @DeleteMapping("/{category_id}")
    public ResponseEntity<ServiceResponseBean> deleteCategory(@PathVariable(value = "category_id") @NotBlank(message = "Category Id can not be empty.") String categoryId) {
        return ResponseEntity.ok(this.blogStackCategoryMasterService.deleteCategory(categoryId)).getBody();
    }
}
