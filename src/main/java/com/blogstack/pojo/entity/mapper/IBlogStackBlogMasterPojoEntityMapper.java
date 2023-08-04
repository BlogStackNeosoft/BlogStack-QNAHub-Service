package com.blogstack.pojo.entity.mapper;


import com.blogstack.beans.requests.BlogMasterRequestBean;
import com.blogstack.entities.BlogStackBlogsMaster;
import com.blogstack.enums.BlogMasterStatusEnum;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;

@Mapper(componentModel = "spring", imports = {LocalDateTime.class, BlogMasterStatusEnum.class})
public interface IBlogStackBlogMasterPojoEntityMapper {

    IBlogStackBlogMasterPojoEntityMapper INSTANCE = Mappers.getMapper(IBlogStackBlogMasterPojoEntityMapper.class);


    @Mappings({
            @Mapping(target = "bsbBlogId", source = "blogsMasterRequestBean.blogId"),
            @Mapping(target = "bsbBlogName", source = "blogsMasterRequestBean.blogName"),
            @Mapping(target = "bsbBlogContent", source = "blogsMasterRequestBean.blogContent"),
            @Mapping(target = "bsbBlogPicture", source = "blogsMasterRequestBean.blogPicture"),
            @Mapping(target = "bsbStatus", expression = "java(BlogMasterStatusEnum.ACTIVE.getValue())"),
            @Mapping(target = "bsbCreatedBy", source = "blogsMasterRequestBean.createdBy"),
            @Mapping(target = "bsbCreatedDate", expression = "java(LocalDateTime.now())")
    })
    BlogStackBlogsMaster blogMasterRequestToQuestionMasterEntity(BlogMasterRequestBean blogsMasterRequestBean);

}
