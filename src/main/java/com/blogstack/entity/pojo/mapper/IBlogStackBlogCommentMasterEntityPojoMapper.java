package com.blogstack.entity.pojo.mapper;

import com.blogstack.beans.responses.BlogCommentMasterResponseBean;
import com.blogstack.beans.responses.CommentMasterResponseBean;
import com.blogstack.entities.BlogStackBlogCommentMaster;
import com.blogstack.entities.BlogStackCommentMaster;


import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public interface IBlogStackBlogCommentMasterEntityPojoMapper {

    public static Function<BlogStackBlogCommentMaster, BlogCommentMasterResponseBean> mapBlogCommentMasterEntityPojoMapping = blogStackBlogCommentMaster -> BlogCommentMasterResponseBean.builder()
            .commentId(blogStackBlogCommentMaster.getBsbcmCommentId())
            .comment(blogStackBlogCommentMaster.getBsbcmComment())
            .status(blogStackBlogCommentMaster.getBsbcmStatus())
            .downvote(blogStackBlogCommentMaster.getBsbcmDownvote())
            .upvote(blogStackBlogCommentMaster.getBsbcmUpvote())
            .addedOn(blogStackBlogCommentMaster.getBsbcmCreatedDate())
            .modifiedOn(blogStackBlogCommentMaster.getBsbcmModifiedDate())
            .build();

    public static Function<Set<BlogStackBlogCommentMaster>, Set<BlogCommentMasterResponseBean>> mapCommentMasterEntityListToPojoListMapping = blogStackBlogCommentMasterList -> blogStackBlogCommentMasterList.stream()
            .map(blogStackBlogCommentMaster -> {
               return  BlogCommentMasterResponseBean.builder().commentId(blogStackBlogCommentMaster.getBsbcmCommentId())
                        .comment(blogStackBlogCommentMaster.getBsbcmComment())
                        .status(blogStackBlogCommentMaster.getBsbcmStatus())
                        .addedOn(blogStackBlogCommentMaster.getBsbcmCreatedDate())
                        .upvote(blogStackBlogCommentMaster.getBsbcmUpvote())
                        .downvote(blogStackBlogCommentMaster.getBsbcmDownvote())
                        .modifiedOn(blogStackBlogCommentMaster.getBsbcmModifiedDate())
                        .build();
            }).collect(Collectors.toSet());
}
