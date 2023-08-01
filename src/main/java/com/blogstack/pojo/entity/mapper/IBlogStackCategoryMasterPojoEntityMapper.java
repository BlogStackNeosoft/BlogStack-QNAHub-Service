package com.blogstack.pojo.entity.mapper;

import com.blogstack.beans.requests.CategoryMasterRequestBean;
import com.blogstack.entities.BlogStackCategoryMaster;
import com.blogstack.enums.CategoryMasterStatusEnum;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;
import java.util.function.BiFunction;

@Mapper(componentModel = "spring", imports = {LocalDateTime.class, CategoryMasterStatusEnum.class})
public interface IBlogStackCategoryMasterPojoEntityMapper {

    IBlogStackCategoryMasterPojoEntityMapper INSTANCE = Mappers.getMapper(IBlogStackCategoryMasterPojoEntityMapper.class);

    @Mappings({
            @Mapping(target = "bscmCategoryId", source = "categoryMasterRequestBean.categoryId"),
            @Mapping(target = "bscmCategory", source = "categoryMasterRequestBean.category"),
            @Mapping(target = "bscmStatus", expression = "java(CategoryMasterStatusEnum.ACTIVE.getValue())"),
            @Mapping(target = "bscmCreatedBy", source = "categoryMasterRequestBean.createdBy"),
            @Mapping(target = "bscmCreatedDate", expression = "java(LocalDateTime.now())")
    })
    BlogStackCategoryMaster categoryMasterRequestToCategoryMasterEntity(CategoryMasterRequestBean categoryMasterRequestBean);

    public static BiFunction<CategoryMasterRequestBean, BlogStackCategoryMaster, BlogStackCategoryMaster> updateCategoryMaster = (categoryMasterRequestBean, blogStackCategoryMaster) -> {
        blogStackCategoryMaster.setBscmCategoryId(categoryMasterRequestBean.getCategoryId() != null ? categoryMasterRequestBean.getCategoryId() : blogStackCategoryMaster.getBscmCategoryId());
        blogStackCategoryMaster.setBscmCategory(categoryMasterRequestBean.getCategory() != null ? categoryMasterRequestBean.getCategory() : blogStackCategoryMaster.getBscmCategory());
        blogStackCategoryMaster.setBscmStatus(categoryMasterRequestBean.getStatus() != null ? categoryMasterRequestBean.getStatus() : blogStackCategoryMaster.getBscmStatus());
        blogStackCategoryMaster.setBscmModifiedBy(categoryMasterRequestBean.getModifiedBy());
        blogStackCategoryMaster.setBscmModifiedDate(LocalDateTime.now());
        return blogStackCategoryMaster;
    };
}