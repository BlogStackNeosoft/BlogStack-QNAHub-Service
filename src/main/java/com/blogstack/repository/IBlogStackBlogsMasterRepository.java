package com.blogstack.repository;

import com.blogstack.entities.BlogStackBlogsMaster;
import com.blogstack.entities.BlogStackQuestionMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IBlogStackBlogsMasterRepository extends JpaRepository<BlogStackBlogsMaster , Long> {

    Optional<BlogStackBlogsMaster> findByBsbBlogId(String blogId);
}
