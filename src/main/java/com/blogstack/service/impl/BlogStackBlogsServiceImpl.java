package com.blogstack.service.impl;

import com.blogstack.beans.requests.BlogMasterRequestBean;
import com.blogstack.beans.responses.ServiceResponseBean;
import com.blogstack.entities.BlogStackBlogsMaster;
import com.blogstack.entity.pojo.mapper.IBlogStackBlogMasterEntityPojoMapper;
import com.blogstack.entity.pojo.mapper.IBlogStackQuestionMasterEntityPojoMapper;
import com.blogstack.enums.UuidPrefixEnum;
import com.blogstack.pojo.entity.mapper.IBlogStackBlogMasterPojoEntityMapper;
import com.blogstack.pojo.entity.mapper.IBlogStackQuestionMasterPojoEntityMapper;
import com.blogstack.repository.IBlogStackBlogsMasterRepository;
import com.blogstack.service.IBlogStackBlogsService;
import com.blogstack.utils.BlogStackCommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class BlogStackBlogsServiceImpl implements IBlogStackBlogsService {

   @Autowired
   private IBlogStackBlogsMasterRepository blogStackBlogsMasterRepository;
    @Value("#{'${spring.application.name}'.toUpperCase()}")
    private String springApplicationName;
   private static Logger LOGGER = LoggerFactory.getLogger(BlogStackBlogsServiceImpl.class);


    @Override
    public Optional<ServiceResponseBean> addBlog(BlogMasterRequestBean blogsRequestBean) {
        String blogId = BlogStackCommonUtils.INSTANCE.uniqueIdentifier(UuidPrefixEnum.BLOG_ID.getValue());
        LOGGER.warn("BlogId :: {}", blogId);
        blogsRequestBean.setBlogId(blogId);
        blogsRequestBean.setCreatedBy(springApplicationName);
        BlogStackBlogsMaster blogStackBlogMaster = this.blogStackBlogsMasterRepository.saveAndFlush(IBlogStackBlogMasterPojoEntityMapper.INSTANCE.blogMasterRequestToQuestionMasterEntity(blogsRequestBean));
        return Optional.of(ServiceResponseBean.builder().status(Boolean.TRUE).data(IBlogStackBlogMasterEntityPojoMapper.mapBlogMasterEntityPojoMapping.apply(Optional.of(blogStackBlogMaster))).build());

    }

    @Override
    public Optional<ServiceResponseBean> fetchAllBlogs(Integer page, Integer size) {
        return Optional.empty();
    }

    @Override
    public Optional<ServiceResponseBean> fetchBlogById(String blogId) {
        return Optional.empty();
    }

    @Override
    public Optional<ServiceResponseBean> updateBlog(BlogMasterRequestBean blogsRequestBean) {
        return Optional.empty();
    }

    @Override
    public Optional<ServiceResponseBean> deleteBlog(String blogId) {
        return Optional.empty();
    }
}
