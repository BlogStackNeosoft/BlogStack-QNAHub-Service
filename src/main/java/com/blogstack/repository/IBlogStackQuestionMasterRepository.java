package com.blogstack.repository;

import com.blogstack.controller.BlogStackQuestionMasterController;
import com.blogstack.entities.BlogStackQuestionMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface IBlogStackQuestionMasterRepository extends JpaRepository<BlogStackQuestionMaster, Long> {

    Optional<BlogStackQuestionMaster> findByBsqmTitleIgnoreCase(String question);

    Optional<BlogStackQuestionMaster> findByBsqmQuestionId(String questionId);

    Optional<Set<BlogStackQuestionMaster>> findByBsqmQuestionIdIn(Set<String> questionId);

    Optional<List<BlogStackQuestionMaster>> findAllByBsqmUserId(String userId);
}