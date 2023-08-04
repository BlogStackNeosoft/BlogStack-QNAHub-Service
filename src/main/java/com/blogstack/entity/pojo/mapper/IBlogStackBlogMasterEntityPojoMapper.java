package com.blogstack.entity.pojo.mapper;

import com.blogstack.beans.responses.BlogMasterResponseBean;
import com.blogstack.entities.BlogStackBlogsMaster;

import java.util.Optional;
import java.util.function.Function;

public interface IBlogStackBlogMasterEntityPojoMapper {

    public static Function<Optional<BlogStackBlogsMaster>, BlogMasterResponseBean> mapBlogMasterEntityPojoMapping = blogStackBlogMaster-> BlogMasterResponseBean.builder()
            .blogId(blogStackBlogMaster.isEmpty()?    null  :  blogStackBlogMaster.get().getBsbBlogId())
            .blogName(blogStackBlogMaster.isEmpty()?    null  :blogStackBlogMaster.get().getBsbBlogName())
            .blogContent(blogStackBlogMaster.isEmpty()?    null  :blogStackBlogMaster.get().getBsbBlogContent())
            .blogPicture(blogStackBlogMaster.isEmpty()?    null  :blogStackBlogMaster.get().getBsbBlogPicture())
            .status(blogStackBlogMaster.isEmpty()?    null  :blogStackBlogMaster.get().getBsbStatus())
            .addedOn(blogStackBlogMaster.isEmpty()?    null  :blogStackBlogMaster.get().getBsbCreatedDate())
            .build();
}
