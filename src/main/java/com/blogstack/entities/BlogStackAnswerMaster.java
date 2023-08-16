package com.blogstack.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "blogstack_answer_master", schema = "master_data")
public class BlogStackAnswerMaster implements Serializable {

    private static final Long searialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bsam_seq_id")
    private Long bsamSeqId;

    @Column(name = "bsam_answer_id")
    private String bsamAnswerId;

    @Column(name = "bsam_answer", columnDefinition = "TEXT")
    private String bsamAnswer;

    @Column(name = "bsam_user_id")
    private String bsamUserId;

    @Column(name = "bsam_upvote")
    private Integer bsamUpvote;

    @Column(name = "bsam_downvote")
    private Integer bsamDownvote;

    @Column(name = "bsam_status")
    private String bsamStatus;

    @CreatedBy
    @Column(name = "bsam_created_by")
    private String bsamCreatedBy;

    @CreatedDate
    @Column(name = "bsam_created_date")
    private LocalDateTime bsamCreatedDate;

    @LastModifiedBy
    @Column(name = "bsam_modified_by")
    private String bsamModifiedBy;

    @LastModifiedDate
    @Column(name = "bsam_modified_date")
    private LocalDateTime bsamModifiedDate;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @JoinColumn(name = "bscm_answer_id", referencedColumnName = "bsam_answer_id")
    private List<BlogStackCommentMaster> blogStackCommentMastersList;
}