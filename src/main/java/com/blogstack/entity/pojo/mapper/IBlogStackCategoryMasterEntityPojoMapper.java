package com.blogstack.entity.pojo.mapper;

import com.blogstack.beans.responses.CategoryMasterResponseBean;
import com.blogstack.entities.BlogStackCategoryMaster;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public interface IBlogStackCategoryMasterEntityPojoMapper {
    public static Function<BlogStackCategoryMaster, CategoryMasterResponseBean> mapCategoryMasterEntityPojoMapping = blogStackCategoryMaster -> CategoryMasterResponseBean.builder()
            .categoryId(blogStackCategoryMaster.getBscmCategoryId())
            .category(blogStackCategoryMaster.getBscmCategory())
            .status(blogStackCategoryMaster.getBscmStatus())
            .addedOn(blogStackCategoryMaster.getBscmCreatedDate())
            .build();

    public static Function<List<BlogStackCategoryMaster>, List<CategoryMasterResponseBean>> mapCategoryMasterEntityListToPojoListMapping = blogStackCategoryMasterList -> blogStackCategoryMasterList.stream()
            .map(blogStackCategoryMaster -> {
                CategoryMasterResponseBean.CategoryMasterResponseBeanBuilder categoryMasterResponseBeanBuilder = CategoryMasterResponseBean.builder();
                categoryMasterResponseBeanBuilder.categoryId(blogStackCategoryMaster.getBscmCategoryId())
                        .category(blogStackCategoryMaster.getBscmCategory())
                        .status(blogStackCategoryMaster.getBscmStatus())
                        .addedOn(blogStackCategoryMaster.getBscmCreatedDate());
                return categoryMasterResponseBeanBuilder.build();
            }).collect(Collectors.toList());

}
