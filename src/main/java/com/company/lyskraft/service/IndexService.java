package com.company.lyskraft.service;

import com.company.lyskraft.entity.ShanghaiStockExchangeData;
import com.company.lyskraft.constant.IndexFutureType;
import com.company.lyskraft.repository.ShanghaiStockExchangeDataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class IndexService {
    private final ShanghaiStockExchangeDataRepository shanghaiStockExchangeDataRepository;

    public Page<ShanghaiStockExchangeData> getAllIndexByFutureType(IndexFutureType futureType, Pageable page)
            throws Exception {
        return shanghaiStockExchangeDataRepository.findAllByNameStartsWith(futureType, page);
    }
}