package com.company.lyskraft.service;

import com.company.lyskraft.entity.LeafCategory;
import com.company.lyskraft.entity.Product;
import com.company.lyskraft.entity.RootCategory;
import com.company.lyskraft.entity.Sku;
import com.company.lyskraft.repository.LeafCategoryRepository;
import com.company.lyskraft.repository.ProductRepository;
import com.company.lyskraft.repository.RootCategoryRepository;
import com.company.lyskraft.repository.SkuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SkuService {
    private final RootCategoryRepository rootCategoryRepository;
    private final LeafCategoryRepository leafCategoryRepository;
    private final SkuRepository skuRepository;
    private final ProductRepository productRepository;

    public Page<RootCategory> getAllRootCategories(Pageable page) {
        return rootCategoryRepository.findAll(page);
    }

    public Page<LeafCategory> getAllLeafCategoriesByRootCategory(long rootCategoryId, Pageable page) {
        return leafCategoryRepository.findAllByRootCategoryId(rootCategoryId, page);
    }

    @Cacheable("skus")
    public Page<Sku> getAllSkus(Pageable page) {
        return skuRepository.findAll(page);
    }

    public Page<Sku> getAllSkusByLeafCategory(long leafCategoryId, Pageable page) {
        return skuRepository.findAllByLeafCategoryId(leafCategoryId, page);
    }

    public Sku getSku(long skuId) throws Exception {
        Optional<Sku> sku = skuRepository.findById(skuId);
        if (sku.isPresent()) {
            Sku skuDetails = sku.get();
            Iterable<Product> products = productRepository.findAllBySkuId(skuId);
            skuDetails.setProducts(products);
            return skuDetails;
        }
        throw new Exception("SKU not present");
    }
}