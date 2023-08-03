package com.blogstack.service.impl;

import com.blogstack.beans.requests.BlogCommentMasterRequestBean;
import com.blogstack.beans.responses.ServiceResponseBean;
import com.blogstack.commons.BlogStackMessageConstants;
import com.blogstack.entities.BlogStackAnswerMaster;
import com.blogstack.entities.BlogStackBlogsMaster;
import com.blogstack.entity.pojo.mapper.IBlogStackAnswerMasterEntityPojoMapper;
import com.blogstack.entity.pojo.mapper.IBlogStackBlogMasterEntityPojoMapper;
import com.blogstack.enums.UuidPrefixEnum;
import com.blogstack.exceptions.BlogstackDataNotFoundException;
import com.blogstack.pojo.entity.mapper.IBlogStackBlogCommentMasterPojoEntityMapper;
import com.blogstack.pojo.entity.mapper.IBlogStackCommentMasterPojoEntityMapper;
import com.blogstack.repository.IBlogStackBlogCommentMasterRepository;
import com.blogstack.repository.IBlogStackBlogsMasterRepository;
import com.blogstack.service.IBlogStackBlogCommentMasterService;
import com.blogstack.utils.BlogStackCommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BlogStackBlogCommentMasterService implements IBlogStackBlogCommentMasterService {
    @Autowired
    private IBlogStackBlogCommentMasterRepository blogStackBlogCommentMasterRepository;

    @Autowired
    private IBlogStackBlogsMasterRepository blogStackBlogsMasterRepository;

    private Logger LOGGER = LoggerFactory.getLogger(BlogStackBlogCommentMasterService.class);


    @Value("#{'${spring.application.name}'.toUpperCase()}")
    private String springApplicationName;

    @Override
    public Optional<ServiceResponseBean> addBlogComment(String blogId, BlogCommentMasterRequestBean blogCommentMasterRequestBean) {

        String commentId = BlogStackCommonUtils.INSTANCE.uniqueIdentifier(UuidPrefixEnum.COMMENT_ID.getValue());
        LOGGER.info("CommentId :: {}", commentId);

        blogCommentMasterRequestBean.setCommentId(commentId);
        blogCommentMasterRequestBean.setCreatedBy(springApplicationName);

        Optional<BlogStackBlogsMaster> blogStackBlogsMasterOptional = this.blogStackBlogsMasterRepository.findByBsbBlogId(blogId);
        LOGGER.warn("BlogStackBlogMasterOptional :: {}", blogStackBlogsMasterOptional);

        if (blogStackBlogsMasterOptional.isEmpty())
            throw new BlogstackDataNotFoundException(BlogStackMessageConstants.INSTANCE.DATA_NOT_FOUND);

        Optional<BlogStackBlogsMaster> blogStackBlogsCommentMasterOptional = blogStackBlogsMasterOptional.map(blog -> {
            blog.getBlogStackBlogCommentMasterSet().add(IBlogStackBlogCommentMasterPojoEntityMapper.INSTANCE.blogCommentMasterRequestToCommentMasterEntity(blogCommentMasterRequestBean));
            return this.blogStackBlogsMasterRepository.saveAndFlush(blog);
        });
        return Optional.of(ServiceResponseBean.builder().status(Boolean.TRUE).data(IBlogStackBlogMasterEntityPojoMapper.mapBlogMasterEntityPojoMapping.apply(blogStackBlogsMasterOptional)).build());

    }

    @Override
    public Optional<ServiceResponseBean> fetchAllBlogComment(Integer page, Integer size) {
        return Optional.empty();
    }

    @Override
    public Optional<ServiceResponseBean> fetchAllBlogCommentsByBlogId(String answerId) {
        return Optional.empty();
    }

    @Override
    public Optional<ServiceResponseBean> fetchBlogCommentById(String commentId) {
        return Optional.empty();
    }

    @Override
    public Optional<ServiceResponseBean> updateBlogComment(BlogCommentMasterRequestBean blogCommentMasterRequestBean) {
        return Optional.empty();
    }

    @Override
    public Optional<ServiceResponseBean> deleteBlogComment(String blogId) {
        return Optional.empty();
    }

    @Override
    public Optional<ServiceResponseBean> deleteAllBlogCommentByBlogId(String blogId) {
        return Optional.empty();
    }
}
