package com.blogstack.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "blogstack_blogs", schema = "master_data")
public class BlogStackBlogs implements Serializable {
    private static final Long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bsb_seq_id")
    private Long bsbSeqId;
    @Column(name = "bsb_blog_id")
    private String bsbBlogId;
    @Column(name = "bsb_blog_name")
    private String bsbBlogName;
    @Column(name = "bsb_blog_picture")
    private String bsbBlogPicture;
    @Column(name = "bsb_blog_content")
    private String bsbBlogContent;
    @Column(name = "bsb_status")
    private String bsbStatus;
    @Column(name = "bsb_created_by")
    private String bsbCreatedBy;
    @Column(name = "bsb_created_date")
    private LocalDateTime bsbCreatedDate;
    @Column(name = "bsb_modified_by")
    private String bsbModifiedBy;
    @Column(name = "bsb_modified_date")
    private LocalDateTime bsbModifiedDate;
}