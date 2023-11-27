package com.company.lyskraft.controller;

import com.company.lyskraft.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v{apiVersion}/admin/select")
public class SelectObjectController {

    private final SelectObjectService selectObjectService;

    @GetMapping("/sku")
    @ResponseBody
    private Object getAllSkusForSelect(@PathVariable int apiVersion) {
        return ResponseEntity.ok(selectObjectService.getAllSkusForSelect());
    }

    @GetMapping("/enquiry")
    @ResponseBody
    private Object getAllEnquiriesForSelect(@PathVariable int apiVersion) {
        return ResponseEntity.ok(selectObjectService.getAllEnquiryForSelect());
    }

    @GetMapping("/enquiry/{enquiryId}/quote")
    @ResponseBody
    private Object getAllQuotesForSelect(@PathVariable int apiVersion,
                                         @PathVariable long enquiryId) {
        return ResponseEntity.ok(selectObjectService.getAllQuotesForSelect(enquiryId));
    }

    @GetMapping("/company")
    @ResponseBody
    private Object getAllCompaniesForSelect(@PathVariable int apiVersion,
                                            @RequestParam(name="exclude", defaultValue="1") Set<Long> exclude) {
        return ResponseEntity.ok(selectObjectService.getAllActiveCompanies(exclude));
    }

    @GetMapping("/country")
    @ResponseBody
    private Object getAllCountriesForSelect(@PathVariable int apiVersion) {
        return ResponseEntity.ok(selectObjectService.getAllCountries());
    }
}
