package com.company.lyskraft.repository;

import com.company.lyskraft.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Iterable<Product> findAllBySkuId(long stockKeepingUnitId);
    Optional<Product> findByTitle(String title);
}