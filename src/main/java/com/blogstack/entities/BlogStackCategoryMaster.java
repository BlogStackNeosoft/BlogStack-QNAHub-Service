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

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class BlogStackCategoryMaster implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bscm_seq_id")
    private Long bscmSeqId;

    @Column(name = "bscm_category_id")
    private String bscmCategoryId;

    @Column(name = "bscm_category")
    private String bscmCategory;

    @Column(name = "bscm_status")
    private String bscmStatus;

    @CreatedBy
    @Column(name = "bscm_created_by")
    private String bscmCreatedBy;

    @CreatedDate
    @Column(name = "bscm_created_date")
    private LocalDateTime bscmCreatedDate;

    @LastModifiedBy
    @Column(name = "bscm_modified_by")
    private String bscmModifiedBy;

    @LastModifiedDate
    @Column(name = "bscm_modified_date")
    private LocalDateTime bscmModifiedDate;

}
