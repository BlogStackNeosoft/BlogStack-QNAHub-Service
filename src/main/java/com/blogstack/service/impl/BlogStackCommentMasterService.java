package com.blogstack.service.impl;

import com.blogstack.beans.requests.CommentMasterRequestBean;
import com.blogstack.beans.responses.PageResponseBean;
import com.blogstack.beans.responses.ServiceResponseBean;
import com.blogstack.commons.BlogStackMessageConstants;
import com.blogstack.entities.BlogStackAnswerMaster;
import com.blogstack.entities.BlogStackCommentMaster;
import com.blogstack.entities.BlogStackQuestionMaster;
import com.blogstack.entity.pojo.mapper.IBlogStackAnswerMasterEntityPojoMapper;
import com.blogstack.entity.pojo.mapper.IBlogStackCommentMasterEntityPojoMapper;
import com.blogstack.enums.CommentMasterStatusEnum;
import com.blogstack.enums.UuidPrefixEnum;
import com.blogstack.exceptions.BlogstackDataNotFoundException;
import com.blogstack.pojo.entity.mapper.IBlogStackAnswerMasterPojoEntityMapper;
import com.blogstack.pojo.entity.mapper.IBlogStackCommentMasterPojoEntityMapper;
import com.blogstack.repository.IBlogStackAnswerMasterRepository;
import com.blogstack.repository.IBlogStackCommentMasterRepository;
import com.blogstack.service.IBlogStackCommentMasterService;
import com.blogstack.utils.BlogStackCommonUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BlogStackCommentMasterService implements IBlogStackCommentMasterService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BlogStackCommentMasterService.class);

    @Value("#{'${spring.application.name}'.toUpperCase()}")
    private String springApplicationName;

    @Autowired
    private IBlogStackCommentMasterRepository blogStackCommentMasterRepository;

    @Autowired
    private IBlogStackAnswerMasterRepository blogStackAnswerMasterRepository;

    @Autowired
    private IBlogStackCommentMasterPojoEntityMapper blogStackQuestionMasterPojoEntityMapper;

    @Override
    public Optional <ServiceResponseBean> addComment(String answerId, CommentMasterRequestBean commentMasterRequestBean) {
        String commentId = BlogStackCommonUtils.INSTANCE.uniqueIdentifier(UuidPrefixEnum.COMMENT_ID.getValue());
        LOGGER.info("CommentId :: {}", commentId);

        commentMasterRequestBean.setCommentId(commentId);
        commentMasterRequestBean.setCreatedBy(springApplicationName);

        Optional<BlogStackAnswerMaster> blogStackAnswerMasterOptional = this.blogStackAnswerMasterRepository.findByBsamAnswerId(answerId);
        LOGGER.warn("BlogStackAnswerMasterOptional :: {}", blogStackAnswerMasterOptional);

        if (blogStackAnswerMasterOptional.isEmpty())
            throw new BlogstackDataNotFoundException(BlogStackMessageConstants.INSTANCE.DATA_NOT_FOUND);

        Optional<BlogStackAnswerMaster> blogStackAnswersCommentMasterOptional = blogStackAnswerMasterOptional.map(answer -> {
            answer.getBlogStackCommentMastersList().add(IBlogStackCommentMasterPojoEntityMapper.INSTANCE.commentMasterRequestToCommentMasterEntity(commentMasterRequestBean));
            return this.blogStackAnswerMasterRepository.saveAndFlush(answer);
        });
        return Optional.of(ServiceResponseBean.builder().status(Boolean.TRUE).data(IBlogStackAnswerMasterEntityPojoMapper.mapAnswerMasterEntityPojoMapping.apply(blogStackAnswersCommentMasterOptional.get())).build());
    }


    @Override
    public Optional<ServiceResponseBean> fetchAllComment(Integer page, Integer size) {
        Page<BlogStackCommentMaster> blogStackCommentMasterPage = this.blogStackCommentMasterRepository.findAll(PageRequest.of(page, size));
        LOGGER.warn("BlogStackCommentMaster :: {}", blogStackCommentMasterPage);

        if (CollectionUtils.isEmpty(blogStackCommentMasterPage.toList()))
            throw new BlogstackDataNotFoundException(BlogStackMessageConstants.INSTANCE.DATA_NOT_FOUND);

        return  Optional.of(ServiceResponseBean.builder()
                .status(Boolean.TRUE).data(PageResponseBean.builder().payload(IBlogStackCommentMasterEntityPojoMapper.mapCommentMasterEntityListToPojoListMapping.apply(blogStackCommentMasterPage.toList()))
                        .numberOfElements(blogStackCommentMasterPage.getNumberOfElements())
                        .pageSize(blogStackCommentMasterPage.getSize())
                        .totalElements(blogStackCommentMasterPage.getTotalElements())
                        .totalPages(blogStackCommentMasterPage.getTotalPages())
                        .currentPage(blogStackCommentMasterPage.getNumber())
                        .build()).build());
    }

    @Override
    public Optional<ServiceResponseBean> fetchAllCommentsByAnswerId(String answerId) {
        Optional<BlogStackAnswerMaster> blogStackAnswerMasterOptional = this.blogStackAnswerMasterRepository.findByBsamAnswerId(answerId);
        LOGGER.warn("BlogStackAnswerMasterOptional :: {}", blogStackAnswerMasterOptional);

        if (blogStackAnswerMasterOptional.isEmpty())
            throw new BlogstackDataNotFoundException(BlogStackMessageConstants.INSTANCE.DATA_NOT_FOUND);

        List<BlogStackCommentMaster> blogStackCommentMasterList = new ArrayList<>(blogStackAnswerMasterOptional.get().getBlogStackCommentMastersList());
        LOGGER.warn("BlogStackCommentMasterList :: {}", blogStackCommentMasterList);

        return Optional.of(ServiceResponseBean.builder().status(Boolean.TRUE).data(IBlogStackCommentMasterEntityPojoMapper.mapCommentMasterEntityListToPojoListMapping.apply(blogStackCommentMasterList)).build());
    }

    @Override
    public Optional<ServiceResponseBean> fetchCommentById(String commentId) {
        Optional<BlogStackCommentMaster> blogStackCommentMasterOptional = this.blogStackCommentMasterRepository.findByBscmCommentId(commentId);
        LOGGER.info("BlogStackQuestionMasterOptional :: {}", blogStackCommentMasterOptional);

        if (blogStackCommentMasterOptional.isEmpty())
            throw new BlogstackDataNotFoundException(BlogStackMessageConstants.INSTANCE.DATA_NOT_FOUND);

        return Optional.of(ServiceResponseBean.builder().status(Boolean.TRUE).data(IBlogStackCommentMasterEntityPojoMapper.mapCommentMasterEntityPojoMapping.apply(blogStackCommentMasterOptional.get())).build());
    }

    @Override
    public Optional<ServiceResponseBean> updateComment(CommentMasterRequestBean commentMasterRequestBean) {
        Optional<BlogStackCommentMaster> blogStackCommenMasterOptional = this.blogStackCommentMasterRepository.findByBscmCommentId(commentMasterRequestBean.getCommentId());
        LOGGER.warn("BlogStackQuestionMasterOptional :: {}", blogStackCommenMasterOptional);

        if (blogStackCommenMasterOptional.isEmpty())
            throw new BlogstackDataNotFoundException(BlogStackMessageConstants.INSTANCE.DATA_NOT_FOUND);

        commentMasterRequestBean.setModifiedBy(this.springApplicationName);
        BlogStackCommentMaster blogStackQuestionMaster = this.blogStackQuestionMasterPojoEntityMapper.INSTANCE.updateCommentMaster.apply(commentMasterRequestBean, blogStackCommenMasterOptional.get());
        LOGGER.warn("BlogStackQuestionMaster :: {}", blogStackQuestionMaster);

        this.blogStackCommentMasterRepository.saveAndFlush(blogStackQuestionMaster);
        return Optional.of(ServiceResponseBean.builder().status(Boolean.TRUE).data(IBlogStackCommentMasterEntityPojoMapper.mapCommentMasterEntityPojoMapping.apply(blogStackQuestionMaster)).build());
    }

    @Override
    public Optional<ServiceResponseBean> deleteComment(String commentId) {
        Optional<BlogStackCommentMaster> blogStackCommentMasterOptional = this.blogStackCommentMasterRepository.findByBscmCommentId(commentId);
        LOGGER.info("blogStackCommentMasterOptional :: {}", blogStackCommentMasterOptional);

        if (blogStackCommentMasterOptional.isEmpty())
            throw new BlogstackDataNotFoundException(BlogStackMessageConstants.INSTANCE.DATA_NOT_FOUND);

        this.blogStackCommentMasterRepository.delete(blogStackCommentMasterOptional.get());
        return Optional.of(ServiceResponseBean.builder().status(Boolean.TRUE).message(BlogStackMessageConstants.INSTANCE.DATA_DELETED).build());
    }

    @Override
    public Optional<ServiceResponseBean> deleteAllCommentByAnswerId(String answerId) {
        Optional<BlogStackAnswerMaster> blogStackAnswerMasterOptional = this.blogStackAnswerMasterRepository.findByBsamAnswerId(answerId);
        LOGGER.warn("BlogStackAnswerMasterOptional :: {}", blogStackAnswerMasterOptional);

        if (blogStackAnswerMasterOptional.isEmpty())
            throw new BlogstackDataNotFoundException(BlogStackMessageConstants.INSTANCE.DATA_NOT_FOUND);

        blogStackAnswerMasterOptional.get().getBlogStackCommentMastersList().clear();
        this.blogStackAnswerMasterRepository.saveAndFlush(blogStackAnswerMasterOptional.get());
        return Optional.of(ServiceResponseBean.builder().status(Boolean.TRUE).message(BlogStackMessageConstants.INSTANCE.DATA_DELETED).build());
    }
}
