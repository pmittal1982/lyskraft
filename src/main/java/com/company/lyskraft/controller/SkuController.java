package com.company.lyskraft.controller;

import com.company.lyskraft.service.SkuService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v{apiVersion}")
public class SkuController {
    private final SkuService skuService;

    @GetMapping("/category")
    @ResponseBody
    private Object getAllRootCategories(@PathVariable int apiVersion,
                                        @RequestParam(name="page", defaultValue="0") Integer pageNumber,
                                        @RequestParam(name="size", defaultValue="10") Integer size) {
        Pageable page = PageRequest.of(pageNumber, size);
        return ResponseEntity.ok(skuService.getAllRootCategories(page));
    }

    @GetMapping("/category/{rootCategoryId}/leafs")
    @ResponseBody
    private Object getAllLeafCategories(@PathVariable int apiVersion,
                                        @PathVariable("rootCategoryId") Long rootCategoryId,
                                        @RequestParam(name="page", defaultValue="0") Integer pageNumber,
                                        @RequestParam(name="size", defaultValue="10") Integer size) {
        Pageable page = PageRequest.of(pageNumber, size);
        return ResponseEntity.ok(skuService.getAllLeafCategoriesByRootCategory(rootCategoryId, page));
    }
    @GetMapping("/sku")
    @ResponseBody
    private Object getAllSkus(@PathVariable int apiVersion,
                              @RequestParam(name="page", defaultValue="0") Integer pageNumber,
                              @RequestParam(name="size", defaultValue="10") Integer size) {
        Pageable page = PageRequest.of(pageNumber, size, Sort.by("title").ascending());
        return ResponseEntity.ok(skuService.getAllSkus(page));
    }

    @GetMapping("/category/{leafCategoryId}/sku")
    @ResponseBody
    private Object getAllSkusByCategory(@PathVariable int apiVersion,
                                        @PathVariable("leafCategoryId") Long leafCategoryId,
                                        @RequestParam(name="page", defaultValue="0") Integer pageNumber,
                                        @RequestParam(name="size", defaultValue="10") Integer size) {
        Pageable page = PageRequest.of(pageNumber, size);
        return ResponseEntity.ok(skuService.getAllSkusByLeafCategory(leafCategoryId, page));
    }

    @GetMapping("/sku/{skuId}")
    @ResponseBody
    private Object getStockKeepingUnit(@PathVariable int apiVersion,
                                       @PathVariable("skuId") Long skuId) throws Exception {
        return ResponseEntity.ok(skuService.getSku(skuId));
    }
}