package com.company.lyskraft.repository;

import com.company.lyskraft.entity.RootCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RootCategoryRepository extends JpaRepository<RootCategory, Long> {
    Optional<RootCategory> findByName(String name);
}