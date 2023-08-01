package com.blogstack.service;

import com.blogstack.beans.requests.CategoryMasterRequestBean;
import com.blogstack.beans.responses.ServiceResponseBean;
import org.springframework.http.ResponseEntity;

public interface IBlogStackCategoryMasterService {
    ResponseEntity<ServiceResponseBean> addCategory(CategoryMasterRequestBean categoryMasterRequestBean);


    ResponseEntity<ServiceResponseBean> fetchAllCategories(Integer page, Integer size);

    ResponseEntity<ServiceResponseBean> fetchCategoryById(String categoryId);

    ResponseEntity<ServiceResponseBean> updateCategory(CategoryMasterRequestBean categoryMasterRequestBean);

    ResponseEntity<ServiceResponseBean> deleteCategory(String categoryId);
}
