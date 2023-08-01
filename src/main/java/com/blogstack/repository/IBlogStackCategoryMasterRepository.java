package com.blogstack.repository;

import com.blogstack.entities.BlogStackCategoryMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IBlogStackCategoryMasterRepository extends JpaRepository<BlogStackCategoryMaster, Long> {

    Optional<BlogStackCategoryMaster> findByBscmCategoryIgnoreCase(String category);

    Optional<BlogStackCategoryMaster> findByBscmCategoryId(String categoryId);
}
