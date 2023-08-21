package com.blogstack.service;

import com.blogstack.beans.requests.CommentMasterRequestBean;
import com.blogstack.beans.responses.ServiceResponseBean;

import java.util.Optional;

public interface IBlogStackCommentMasterService {
    Optional<ServiceResponseBean> addComment(String answerId, CommentMasterRequestBean commentMasterRequestBean);

    Optional<ServiceResponseBean> fetchAllComment(Integer page, Integer size);

    Optional<ServiceResponseBean> fetchAllCommentsByAnswerId(String answerId);

    Optional<ServiceResponseBean> fetchCommentById(String commentId);

    Optional<ServiceResponseBean> updateComment(CommentMasterRequestBean commentMasterRequestBean);

    Optional<ServiceResponseBean> deleteComment(String commentId);

    Optional<ServiceResponseBean> deleteAllCommentByAnswerId(String answerId);

    Optional<ServiceResponseBean> fetchAllCommentByUserId(String userId);
}