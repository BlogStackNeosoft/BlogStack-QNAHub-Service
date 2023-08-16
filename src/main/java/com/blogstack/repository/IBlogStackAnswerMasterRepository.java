package com.blogstack.repository;

import com.blogstack.entities.BlogStackAnswerMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface IBlogStackAnswerMasterRepository  extends JpaRepository<BlogStackAnswerMaster, Long>{
    Optional<BlogStackAnswerMaster> findByBsamAnswerIgnoreCase(String answer);

    Optional<BlogStackAnswerMaster> findByBsamAnswerId(String answerId);

    Optional<Set<BlogStackAnswerMaster>> findAllByBsamUserId(String emailId);
}