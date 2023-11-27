package com.company.lyskraft.controller;

import com.company.lyskraft.dto.EnquiryTerms;
import com.company.lyskraft.service.MasterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v{apiVersion}")
public class MasterController {

    private final MasterService masterService;

    @GetMapping("/country")
    @ResponseBody
    private Object getAllCountries(@PathVariable int apiVersion) {
        return ResponseEntity.ok(masterService.getAllCountries());
    }

    @GetMapping("/enquiry/terms")
    @ResponseBody
    private Object getAllEnquiryTerms(@PathVariable int apiVersion) {
        return ResponseEntity.ok(new EnquiryTerms());
    }
}