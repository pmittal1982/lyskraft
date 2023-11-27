package com.company.lyskraft.repository;

import com.company.lyskraft.entity.Sku;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SkuRepository extends JpaRepository<Sku, Long> {
    Page<Sku> findAllByLeafCategoryId(long buyerId, Pageable page);
    Optional<Sku> findByTitle(String title);
}