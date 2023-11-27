package com.company.lyskraft.controller;

import com.company.lyskraft.constant.IndexFutureType;
import com.company.lyskraft.service.IndexService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v{apiVersion}/user")
public class IndexController {
    private final IndexService indexService;
    @GetMapping("/news")
    @ResponseBody
    private Object getAllIndexes(@PathVariable int apiVersion,
                                 @RequestParam(name="future", defaultValue="rb") IndexFutureType indexFutureType) throws Exception {
        Pageable page = PageRequest.of(0, 12, Sort.by("lastModifiedDate").descending());
        return ResponseEntity.ok(indexService.getAllIndexByFutureType(indexFutureType, page));
    }
}