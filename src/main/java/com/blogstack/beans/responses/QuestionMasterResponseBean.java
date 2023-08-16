package com.blogstack.beans.responses;

import com.blogstack.commons.BlogStackCommonConstants;
import com.blogstack.entities.BlogStackAnswerMaster;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class QuestionMasterResponseBean {
    @JsonProperty(value = "question_id")
    private String questionId;

    private String content;

    private String title;

    private String status;

    @JsonProperty(value = "user_id")
    private String userId;

    @JsonProperty(value = "code_snippet")
    private String codeSnippet;

    @JsonProperty(value = "tag_id")
    private String tagId;

    @JsonProperty(value = "category_id")
    private String categoryId;

    @JsonProperty(value = "sub_category_id")
    private String subCategoryId;

    @JsonProperty(value = "answers")
    private Set<AnswerMasterResponseBean> blogStackAnswers;

    @JsonProperty(value = "added_on")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = BlogStackCommonConstants.DATE_FORMAT)
    private LocalDateTime addedOn;

    @JsonProperty(value = "modified_on")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = BlogStackCommonConstants.DATE_FORMAT)
    private LocalDateTime modifiedOn;
}