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
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CSVService {

    private final RootCategoryRepository rootCategoryRepository;
    private final LeafCategoryRepository leafCategoryRepository;
    private final SkuRepository skuRepository;
    private final ProductRepository productRepository;

    public boolean hasCSVFormat(MultipartFile file) {
        String TYPE = "text/csv";
        return TYPE.equals(file.getContentType());
    }

    public void uploadData(InputStream is) throws IOException {
        try (
                BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
                CSVParser csvParser = new CSVParser(fileReader, CSVFormat.DEFAULT);
        ) {
            for (CSVRecord csvRecord : csvParser) {
                Optional<RootCategory> rootCategory = rootCategoryRepository.findByName(csvRecord.get(0).trim());
                RootCategory rc;
                if (rootCategory.isEmpty()) {
                    rc = new RootCategory();
                    rc.setName(csvRecord.get(0).trim());
                    rc = rootCategoryRepository.save(rc);
                } else {
                    rc = rootCategory.get();
                }
                Optional<LeafCategory> leafCategory = leafCategoryRepository.findByName(csvRecord.get(1).trim());
                LeafCategory lc;
                if (leafCategory.isEmpty()) {
                    lc = new LeafCategory();
                    lc.setName(csvRecord.get(1).trim());
                    lc.setRootCategory(rc);
                    lc = leafCategoryRepository.save(lc);
                } else {
                    lc = leafCategory.get();
                }
                Optional<Sku> stockKeepingUnit = skuRepository.findByTitle(csvRecord.get(2).trim());
                Sku sku;
                if (stockKeepingUnit.isEmpty()) {
                    sku = new Sku();
                    sku.setTitle(csvRecord.get(2).trim());
                    sku.setDescription(csvRecord.get(4).trim());
                    sku.setLeafCategory(lc);
                    sku = skuRepository.save(sku);
                } else {
                    sku = stockKeepingUnit.get();
                }
                if (!csvRecord.get(3).isBlank()) {
                    String[] products = csvRecord.get(3).split("\\n");
                    for (String pr : products) {
                        Optional<Product> product = productRepository.findByTitle(pr.trim());
                        Product pro;
                        if (product.isEmpty()) {
                            pro = new Product();
                            pro.setTitle(pr.trim());
                            pro.setSku(sku);
                            productRepository.save(pro);
                        }
                    }
                }
            }
        }
    }
}