package com.company.lyskraft.repository;

import com.company.lyskraft.entity.LeafCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LeafCategoryRepository extends JpaRepository<LeafCategory, Long> {
    Page<LeafCategory> findAllByRootCategoryId(long rootCategoryId, Pageable page);
    Optional<LeafCategory> findByName(String name);
}