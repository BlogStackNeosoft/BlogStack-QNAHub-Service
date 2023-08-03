package com.blogstack.service;


import com.blogstack.beans.requests.AnswerMasterRequestBean;
import com.blogstack.beans.requests.BlogCommentMasterRequestBean;
import com.blogstack.beans.requests.CommentMasterRequestBean;
import com.blogstack.beans.responses.ServiceResponseBean;

import java.util.Optional;

public interface IBlogStackBlogCommentMasterService {

    Optional<ServiceResponseBean> addBlogComment(String answerId, BlogCommentMasterRequestBean blogCommentMasterRequestBean);
    Optional<ServiceResponseBean> fetchAllBlogComment(Integer page, Integer size);

    Optional<ServiceResponseBean> fetchAllBlogCommentsByBlogId(String answerId);

    Optional<ServiceResponseBean> fetchBlogCommentById(String commentId);

    Optional<ServiceResponseBean> updateBlogComment(BlogCommentMasterRequestBean blogCommentMasterRequestBean);

    Optional<ServiceResponseBean> deleteBlogComment(String blogId);

    Optional<ServiceResponseBean> deleteAllBlogCommentByBlogId(String blogId);
}
