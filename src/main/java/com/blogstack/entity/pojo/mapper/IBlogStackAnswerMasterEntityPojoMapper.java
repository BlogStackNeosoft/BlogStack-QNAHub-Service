package com.blogstack.entity.pojo.mapper;

import com.blogstack.beans.responses.AnswerMasterResponseBean;
import com.blogstack.beans.responses.CommentMasterResponseBean;
import com.blogstack.entities.BlogStackAnswerMaster;



import java.util.ArrayList;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public interface IBlogStackAnswerMasterEntityPojoMapper {
    public static Function<BlogStackAnswerMaster, AnswerMasterResponseBean> mapAnswerMasterEntityPojoMapping = blogStackAnswerMaster -> AnswerMasterResponseBean.builder()
            .answerId(blogStackAnswerMaster.getBsamAnswerId())
            .answer(blogStackAnswerMaster.getBsamAnswer())
            .status(blogStackAnswerMaster.getBsamStatus())
            .comments(IBlogStackCommentMasterEntityPojoMapper.mapCommentMasterEntityListToPojoListMapping.apply(blogStackAnswerMaster.getBlogStackCommentMastersList()))
            .addedOn(blogStackAnswerMaster.getBsamCreatedDate())
            .build();

    public static Function<Set<BlogStackAnswerMaster>, Set<AnswerMasterResponseBean>> mapAnswerMasterEntityListToPojoListMapping = blogStackAnswerMasterList -> blogStackAnswerMasterList.stream()
            .map(blogStackAnswerMaster -> {
                AnswerMasterResponseBean.AnswerMasterResponseBeanBuilder answerMasterResponseBeanBuilder = AnswerMasterResponseBean.builder();
                answerMasterResponseBeanBuilder.answerId(blogStackAnswerMaster.getBsamAnswerId())
                        .answer(blogStackAnswerMaster.getBsamAnswer())
                        .status(blogStackAnswerMaster.getBsamStatus())
                        //.comments(IBlogStackCommentMasterEntityPojoMapper.mapCommentMasterEntityListToPojoListMapping.apply(blogStackAnswerMaster.getBlogStackCommentMastersList()))
                        .comments(blogStackAnswerMaster.getBlogStackCommentMastersList()==null? new ArrayList<CommentMasterResponseBean>() : IBlogStackCommentMasterEntityPojoMapper.mapCommentMasterEntityListToPojoListMapping.apply(blogStackAnswerMaster.getBlogStackCommentMastersList()))
                        .addedOn(blogStackAnswerMaster.getBsamCreatedDate());
                return answerMasterResponseBeanBuilder.build();
            }).collect(Collectors.toSet());
}