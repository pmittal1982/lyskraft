package com.company.lyskraft.repository;

import com.company.lyskraft.entity.ShanghaiStockExchangeData;
import com.company.lyskraft.constant.IndexFutureType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;

public interface ShanghaiStockExchangeDataRepository extends JpaRepository<ShanghaiStockExchangeData, Long> {
    Page<ShanghaiStockExchangeData> findAllByNameStartsWith(IndexFutureType futureType,
                                                            Pageable page);
    void deleteByCreatedDateBefore(Date expiryDate);
}