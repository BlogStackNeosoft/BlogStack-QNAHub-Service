package com.blogstack.service;

import com.blogstack.beans.requests.BlogMasterRequestBean;
import com.blogstack.beans.responses.ServiceResponseBean;

import java.util.Optional;

public interface IBlogStackBlogsService {

    Optional<ServiceResponseBean> addBlog(BlogMasterRequestBean blogsRequestBean);

    Optional<ServiceResponseBean> fetchAllBlogs(Integer page, Integer size);

    Optional<ServiceResponseBean> fetchBlogById(String blogId);

    Optional<ServiceResponseBean> updateBlog(BlogMasterRequestBean blogsRequestBean);

    Optional<ServiceResponseBean> deleteBlog(String blogId);
}
