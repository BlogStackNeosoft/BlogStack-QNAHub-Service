package com.blogstack.service.impl;

import com.blogstack.beans.requests.CategoryMasterRequestBean;
import com.blogstack.beans.responses.PageResponseBean;
import com.blogstack.beans.responses.ServiceResponseBean;
import com.blogstack.commons.BlogStackMessageConstants;
import com.blogstack.entities.BlogStackCategoryMaster;
import com.blogstack.entity.pojo.mapper.IBlogStackCategoryMasterEntityPojoMapper;
import com.blogstack.enums.CategoryMasterStatusEnum;
import com.blogstack.enums.UuidPrefixEnum;
import com.blogstack.exceptions.BlogStackCustomException;
import com.blogstack.exceptions.BlogstackDataNotFoundException;
import com.blogstack.pojo.entity.mapper.IBlogStackCategoryMasterPojoEntityMapper;
import com.blogstack.repository.IBlogStackCategoryMasterRepository;
import com.blogstack.service.IBlogStackCategoryMasterService;
import com.blogstack.utils.BlogStackCommonUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BlogStackCategoryMasterService implements IBlogStackCategoryMasterService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BlogStackCategoryMasterService.class);
    @Autowired
    private IBlogStackCategoryMasterRepository blogStackCategoryMasterRepository;

    @Value("#{'${spring.application.name}'.toUpperCase()}")
    private String springApplicationName;

    @Autowired
    private IBlogStackCategoryMasterPojoEntityMapper blogStackCategoryMasterPojoEntityMapper;

    @Override
    public ResponseEntity<ServiceResponseBean> addCategory(CategoryMasterRequestBean categoryMasterRequestBean) {
        Optional<BlogStackCategoryMaster> blogStackCategoryMasterOptional = this.blogStackCategoryMasterRepository.findByBscmCategoryIgnoreCase(categoryMasterRequestBean.getCategory());
        LOGGER.warn("BlogStackCategoryMasterOptional :: {}", blogStackCategoryMasterOptional);

        if (blogStackCategoryMasterOptional.isPresent())
            throw new BlogStackCustomException(BlogStackMessageConstants.INSTANCE.ALREADY_EXIST);

        String categoryId = BlogStackCommonUtils.INSTANCE.uniqueIdentifier(UuidPrefixEnum.CATEGORY_ID.getValue());
        LOGGER.warn("categoryId :: {}", categoryId);

        categoryMasterRequestBean.setCategoryId(categoryId);
        categoryMasterRequestBean.setCreatedBy(springApplicationName);

        BlogStackCategoryMaster blogStackCategoryMaster = this.blogStackCategoryMasterRepository.saveAndFlush(this.blogStackCategoryMasterPojoEntityMapper.INSTANCE.categoryMasterRequestToCategoryMasterEntity(categoryMasterRequestBean));
        return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.TRUE).data(IBlogStackCategoryMasterEntityPojoMapper.mapCategoryMasterEntityPojoMapping.apply(blogStackCategoryMaster)).build());
    }

    @Override
    public ResponseEntity<ServiceResponseBean> fetchAllCategories(Integer page, Integer size) {
        Page<BlogStackCategoryMaster> blogStackCategoryMasterPage = this.blogStackCategoryMasterRepository.findAll(PageRequest.of(page, size));
        LOGGER.debug("BlogStackCategoryMaster :: {}", blogStackCategoryMasterPage);

        if (CollectionUtils.isEmpty(blogStackCategoryMasterPage.toList()))
            throw new BlogstackDataNotFoundException(BlogStackMessageConstants.INSTANCE.DATA_NOT_FOUND);

        return ResponseEntity.ok(ServiceResponseBean.builder()
                .status(Boolean.TRUE).data(PageResponseBean.builder().payload(IBlogStackCategoryMasterEntityPojoMapper.mapCategoryMasterEntityListToPojoListMapping.apply(blogStackCategoryMasterPage.toList()))
                        .numberOfElements(blogStackCategoryMasterPage.getNumberOfElements())
                        .pageSize(blogStackCategoryMasterPage.getSize())
                        .totalElements(blogStackCategoryMasterPage.getTotalElements())
                        .totalPages(blogStackCategoryMasterPage.getTotalPages())
                        .currentPage(blogStackCategoryMasterPage.getNumber())
                        .build()).build());
    }

    @Override
    public ResponseEntity<ServiceResponseBean> fetchCategoryById(String categoryId) {
        Optional<BlogStackCategoryMaster> blogStackCategoryMasterOptional = this.blogStackCategoryMasterRepository.findByBscmCategoryId(categoryId);

        if (blogStackCategoryMasterOptional.isEmpty())
            throw new BlogstackDataNotFoundException(BlogStackMessageConstants.INSTANCE.DATA_NOT_FOUND);

        return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.TRUE).data(IBlogStackCategoryMasterEntityPojoMapper.mapCategoryMasterEntityPojoMapping.apply(blogStackCategoryMasterOptional.get())).build());
    }

    @Override
    public ResponseEntity<ServiceResponseBean> updateCategory(CategoryMasterRequestBean categoryMasterRequestBean) {
        Optional<BlogStackCategoryMaster> blogStackCategoryMasterOptional = this.blogStackCategoryMasterRepository.findByBscmCategoryId(categoryMasterRequestBean.getCategoryId());
        LOGGER.warn("BlogStackCategoryMasterOptional :: {}", blogStackCategoryMasterOptional);

        if (blogStackCategoryMasterOptional.isEmpty())
            throw new BlogstackDataNotFoundException(BlogStackMessageConstants.INSTANCE.DATA_NOT_FOUND);

        categoryMasterRequestBean.setModifiedBy(this.springApplicationName);
        BlogStackCategoryMaster blogStackCategoryMaster = IBlogStackCategoryMasterPojoEntityMapper.updateCategoryMaster.apply(categoryMasterRequestBean, blogStackCategoryMasterOptional.get());
        LOGGER.debug("blogStackCategoryMaster :: {}", blogStackCategoryMaster);

        this.blogStackCategoryMasterRepository.saveAndFlush(new BlogStackCategoryMaster());
        return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.TRUE).data(IBlogStackCategoryMasterEntityPojoMapper.mapCategoryMasterEntityPojoMapping.apply(blogStackCategoryMaster)).build());
    }

    @Override
    public ResponseEntity<ServiceResponseBean> deleteCategory(String categoryId) {
        Optional<BlogStackCategoryMaster> blogStackCategoryMasterOptional = this.blogStackCategoryMasterRepository.findByBscmCategoryId(categoryId);
        LOGGER.warn("blogStackCategoryMasterOptional :: {}", blogStackCategoryMasterOptional);

        if (blogStackCategoryMasterOptional.isEmpty())
            throw new BlogstackDataNotFoundException(BlogStackMessageConstants.INSTANCE.DATA_NOT_FOUND);

        blogStackCategoryMasterOptional.get().setBscmStatus(CategoryMasterStatusEnum.DELETED.getValue());
        blogStackCategoryMasterOptional.get().setBscmModifiedBy(springApplicationName);
        this.blogStackCategoryMasterRepository.saveAndFlush(blogStackCategoryMasterOptional.get());
        return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.TRUE).message(BlogStackMessageConstants.INSTANCE.DATA_DELETED).build());
    }
}





