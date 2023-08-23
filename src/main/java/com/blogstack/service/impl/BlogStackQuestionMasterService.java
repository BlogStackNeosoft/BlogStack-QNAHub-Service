package com.blogstack.service.impl;

import com.blogstack.beans.requests.QuestionMasterRequestBean;
import com.blogstack.beans.responses.PageResponseBean;
import com.blogstack.beans.responses.QuestionMasterResponseBean;
import com.blogstack.beans.responses.ServiceResponseBean;
import com.blogstack.commons.BlogStackMessageConstants;
import com.blogstack.entities.BlogStackQuestionMaster;
import com.blogstack.entity.pojo.mapper.IBlogStackQuestionMasterEntityPojoMapper;
import com.blogstack.enums.UuidPrefixEnum;
import com.blogstack.exceptions.BlogStackCustomException;
import com.blogstack.exceptions.BlogStackDataNotFoundException;
import com.blogstack.feign.clients.IBlogStackUserFeignClient;
import com.blogstack.pojo.entity.mapper.IBlogStackQuestionMasterPojoEntityMapper;
import com.blogstack.repository.IBlogStackAnswerMasterRepository;
import com.blogstack.repository.IBlogStackQuestionMasterRepository;
import com.blogstack.service.IBlogStackQuestionMasterService;
import com.blogstack.utils.BlogStackCommonUtils;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class BlogStackQuestionMasterService implements IBlogStackQuestionMasterService {
    private static final Logger LOGGER = LoggerFactory.getLogger(BlogStackQuestionMasterService.class);

    @Value("#{'${spring.application.name}'.toUpperCase()}")
    private String springApplicationName;
    private IBlogStackQuestionMasterRepository blogStackQuestionMasterRepository;
    private IBlogStackAnswerMasterRepository blogStackAnswerMasterRepository;
    private IBlogStackUserFeignClient blogStackUserFeignClient;
    private ObjectMapper objectMapper;

    public BlogStackQuestionMasterService(IBlogStackQuestionMasterRepository blogStackQuestionMasterRepository, IBlogStackAnswerMasterRepository blogStackAnswerMasterRepository, IBlogStackUserFeignClient blogStackUserFeignClient, ObjectMapper objectMapper) {
        this.blogStackQuestionMasterRepository = blogStackQuestionMasterRepository;
        this.blogStackAnswerMasterRepository = blogStackAnswerMasterRepository;
        this.blogStackUserFeignClient = blogStackUserFeignClient;
        this.objectMapper = objectMapper;
    }

    @Override
    public Optional<ServiceResponseBean> addQuestion(QuestionMasterRequestBean questionMasterRequestBean) {
        Optional<BlogStackQuestionMaster> blogStackQuestionMasterOptional = this.blogStackQuestionMasterRepository.findByBsqmTitleIgnoreCase(questionMasterRequestBean.getTitle());
        LOGGER.warn("BlogStackQuestionMasterOptional :: {}", blogStackQuestionMasterOptional);

        if (blogStackQuestionMasterOptional.isPresent())
            throw new BlogStackCustomException(BlogStackMessageConstants.INSTANCE.ALREADY_EXIST);

        String questionId = BlogStackCommonUtils.INSTANCE.uniqueIdentifier(UuidPrefixEnum.QUESTION_ID.getValue());
        LOGGER.warn("QuestionId :: {}", questionId);

        questionMasterRequestBean.setQuestionId(questionId);
        questionMasterRequestBean.setCreatedBy(springApplicationName);
        BlogStackQuestionMaster blogStackQuestionMaster = this.blogStackQuestionMasterRepository.saveAndFlush(IBlogStackQuestionMasterPojoEntityMapper.INSTANCE.questionMasterRequestToQuestionMasterEntity(questionMasterRequestBean));
        return Optional.of(ServiceResponseBean.builder().status(Boolean.TRUE).data(IBlogStackQuestionMasterEntityPojoMapper.mapQuestionMasterEntityPojoMapping.apply(blogStackQuestionMaster)).build());
    }

    @Override
    public Optional<ServiceResponseBean> fetchAllQuestion(Integer page, Integer size) {
        Page<BlogStackQuestionMaster> blogStackQuestionMasterPage = this.blogStackQuestionMasterRepository.findAll(PageRequest.of(page, size));
        LOGGER.info("BlogStackQuestionMaster :: {}", blogStackQuestionMasterPage);

        if (CollectionUtils.isEmpty(blogStackQuestionMasterPage.toList()))
            throw new BlogStackDataNotFoundException(BlogStackMessageConstants.INSTANCE.DATA_NOT_FOUND);

//        List<QuestionMasterResponseBean> questionMasterResponseBeanList = new ArrayList<>();
//        blogStackQuestionMasterPage.getContent().stream().map(question -> {
//            JsonNode body = this.objectMapper.convertValue(this.blogStackUserFeignClient.fetchUserById(question.getBsqmUserId()).getBody(), JsonNode.class);
//            JsonNode jsonNode = body.get("data");
//            ((ObjectNode ) jsonNode).remove("user_roles");
//            QuestionMasterResponseBean questionMasterResponseBean = IBlogStackQuestionMasterEntityPojoMapper.mapQuestionMasterEntityPojoMapping.apply(question);
//            questionMasterResponseBean.setUser(jsonNode);
//            questionMasterResponseBeanList.add(questionMasterResponseBean);
//            return questionMasterResponseBean;
//        }).toList();

        return Optional.of(ServiceResponseBean.builder()
                .status(Boolean.TRUE).data(PageResponseBean.builder().payload(IBlogStackQuestionMasterEntityPojoMapper.mapQuestionMasterEntityListToPojoListMapping.apply(blogStackQuestionMasterPage.toList()))
                        .numberOfElements(blogStackQuestionMasterPage.getNumberOfElements())
                        .pageSize(blogStackQuestionMasterPage.getSize())
                        .totalElements(blogStackQuestionMasterPage.getTotalElements())
                        .totalPages(blogStackQuestionMasterPage.getTotalPages())
                        .currentPage(blogStackQuestionMasterPage.getNumber())
                        .build()).build());
    }

    @Override
    public Optional<ServiceResponseBean> fetchQuestionById(String questionId) {
        Optional<BlogStackQuestionMaster> blogStackQuestionMasterOptional = this.blogStackQuestionMasterRepository.findByBsqmQuestionId(questionId);
        LOGGER.info("BlogStackQuestionMasterOptional :: {}", blogStackQuestionMasterOptional);

        if(blogStackQuestionMasterOptional.isEmpty())
            throw new BlogStackDataNotFoundException(BlogStackMessageConstants.INSTANCE.DATA_NOT_FOUND);

//        JsonNode body = this.objectMapper.convertValue(this.blogStackUserFeignClient.fetchUserById(blogStackQuestionMasterOptional.get().getBsqmUserId()).getBody(), JsonNode.class);
//        LOGGER.info("body data :: {}", body);
//
//        JsonNode jsonNode = body.get("data");
//        LOGGER.info("JsonNode :: {}", jsonNode);
//
//        ((ObjectNode ) jsonNode).remove("user_roles");

        Object blogStackUserResponseBody;
        try {
            blogStackUserResponseBody = this.blogStackUserFeignClient.fetchUserById(blogStackQuestionMasterOptional.get().getBsqmUserId()).getBody();
        }
        catch (Exception exception){
            throw new BlogStackDataNotFoundException(BlogStackMessageConstants.INSTANCE.DATA_NOT_FOUND);
        }
        LOGGER.info("BlogStackUserResponseBody :: {}", blogStackUserResponseBody);

        JsonNode jsonNode = this.objectMapper.convertValue(blogStackUserResponseBody, JsonNode.class);
        LOGGER.info("jsonNode :: {}", jsonNode);

        JsonNode blogStackUser = jsonNode.get("data");
        LOGGER.info("blogStackUser :: {}", blogStackUser);

        ((ObjectNode) blogStackUser).remove("user_roles");

        QuestionMasterResponseBean questionMasterResponseBean = IBlogStackQuestionMasterEntityPojoMapper.mapQuestionMasterEntityPojoMapping.apply(blogStackQuestionMasterOptional.get());
        questionMasterResponseBean.setUser(blogStackUser);

        return Optional.of(ServiceResponseBean.builder().status(Boolean.TRUE).data(questionMasterResponseBean).build());
    }

    @Override
    public Optional<ServiceResponseBean> updateQuestion(QuestionMasterRequestBean questionMasterRequestBean) {
        Optional<BlogStackQuestionMaster> blogStackQuestionMasterOptional = this.blogStackQuestionMasterRepository.findByBsqmQuestionId(questionMasterRequestBean.getQuestionId());
        LOGGER.debug("BlogStackQuestionMasterOptional :: {}", blogStackQuestionMasterOptional);

        if (blogStackQuestionMasterOptional.isEmpty())
            throw new BlogStackDataNotFoundException(BlogStackMessageConstants.INSTANCE.DATA_NOT_FOUND);

        questionMasterRequestBean.setModifiedBy(this.springApplicationName);
        BlogStackQuestionMaster blogStackQuestionMaster = IBlogStackQuestionMasterPojoEntityMapper.updateQuestionMaster.apply(questionMasterRequestBean, blogStackQuestionMasterOptional.get());
        LOGGER.debug("BlogStackQuestionMaster :: {}", blogStackQuestionMaster);

        this.blogStackQuestionMasterRepository.saveAndFlush(blogStackQuestionMaster);
        return Optional.of(ServiceResponseBean.builder().status(Boolean.TRUE).data(IBlogStackQuestionMasterEntityPojoMapper.mapQuestionMasterEntityPojoMapping.apply(blogStackQuestionMaster)).build());
    }

    @Override
    public Optional<ServiceResponseBean> deleteQuestion(String questionId) {
        Optional<BlogStackQuestionMaster> blogStackQuestionMasterOptional = this.blogStackQuestionMasterRepository.findByBsqmQuestionId(questionId);
        LOGGER.warn("BlogStackQuestionMasterOptional :: {}", blogStackQuestionMasterOptional);

        if (blogStackQuestionMasterOptional.isEmpty())
            throw new BlogStackDataNotFoundException(BlogStackMessageConstants.INSTANCE.DATA_NOT_FOUND);

        this.blogStackQuestionMasterRepository.delete(blogStackQuestionMasterOptional.get());
        return Optional.of(ServiceResponseBean.builder().status(Boolean.TRUE).message(BlogStackMessageConstants.INSTANCE.DATA_DELETED).build());
    }

    @Override
    public Optional<ServiceResponseBean> fetchAllQuestionsByQuestionIds(Set<String> ids) {
        Optional<Set<BlogStackQuestionMaster>> blogStackQuestionMasterOptionalSet = this.blogStackQuestionMasterRepository.findByBsqmQuestionIdIn(ids);
        LOGGER.warn("BlogStackQuestionMasterOptionalSet :: {}", blogStackQuestionMasterOptionalSet);

        if (blogStackQuestionMasterOptionalSet.isPresent() && blogStackQuestionMasterOptionalSet.get().isEmpty())
            throw new BlogStackDataNotFoundException(BlogStackMessageConstants.INSTANCE.DATA_NOT_FOUND);

        List<BlogStackQuestionMaster> blogStackQuestionMasterList = new ArrayList<>(blogStackQuestionMasterOptionalSet.get());

        return Optional.of(ServiceResponseBean.builder().status(Boolean.TRUE).data(IBlogStackQuestionMasterEntityPojoMapper.mapQuestionMasterEntityListToPojoListMapping.apply(blogStackQuestionMasterList)).build());
    }

    @Override
    public Optional<ServiceResponseBean> fetchAllQuestionsByUserId(String userId) {
        Optional<List<BlogStackQuestionMaster>> blogStackQuestionMasterOptionalList = this.blogStackQuestionMasterRepository.findAllByBsqmUserId(userId);
        LOGGER.warn("BlogStackQuestionMasterOptionalList :: {}", blogStackQuestionMasterOptionalList);

        if (blogStackQuestionMasterOptionalList.isPresent() && blogStackQuestionMasterOptionalList.get().isEmpty())
            throw new BlogStackDataNotFoundException(BlogStackMessageConstants.INSTANCE.DATA_NOT_FOUND);

        return Optional.of(ServiceResponseBean.builder().status(Boolean.TRUE).data(IBlogStackQuestionMasterEntityPojoMapper.mapQuestionMasterEntityListToPojoListMapping.apply(blogStackQuestionMasterOptionalList.get())).build());
    }
}