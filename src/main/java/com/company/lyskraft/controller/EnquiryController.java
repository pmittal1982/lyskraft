package com.company.lyskraft.controller;

import com.company.lyskraft.constant.UserRole;
import com.company.lyskraft.entity.Enquiry;
import com.company.lyskraft.entity.User;
import com.company.lyskraft.constant.EnquiryStatus;
import com.company.lyskraft.constant.EnquiryType;
import com.company.lyskraft.service.EnquiryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v{apiVersion}/user")
public class EnquiryController {
    private final EnquiryService enquiryService;

    @PostMapping("/enquiry")
    @ResponseBody
    private Object createEnquiry(@PathVariable int apiVersion,
                                 @Valid @RequestBody Enquiry enquiryDetails,
                                 Authentication authentication) throws Exception {
        User user = (User)authentication.getPrincipal();
        if (!user.getRole().equals(UserRole.ROOT)) {
            enquiryDetails.setEnquiryCompany(user.getCompany());
        }
        return ResponseEntity.ok(enquiryService.createEnquiry(enquiryDetails));
    }

    @PutMapping("/enquiry/{enquiryId}")
    @ResponseBody
    private Object updateEnquiryStatus(@PathVariable int apiVersion,
                                       Authentication authentication,
                                       @PathVariable("enquiryId") Long enquiryId,
                                       @Valid @RequestBody Map<String,EnquiryStatus> status) throws Exception {
        User user = (User)authentication.getPrincipal();
        return ResponseEntity.ok(enquiryService.updateEnquiryStatus(user, enquiryId, status.get("status")));
    }

    @GetMapping("/enquiry")
    @ResponseBody
    private Object getAllEnquiriesByCurrentUser(@PathVariable int apiVersion,
                                                Authentication authentication,
                                                @RequestParam(name="page", defaultValue="0") Integer pageNumber,
                                                @RequestParam(name="size", defaultValue="10") Integer size,
                                                @RequestParam(name="status", defaultValue="Inreview,Active,Complete") Set<EnquiryStatus> enquiryStatus) {
        User user = (User)authentication.getPrincipal();
        Pageable page = PageRequest.of(pageNumber, size, Sort.by("lastModifiedDate").descending());
        return ResponseEntity.ok(enquiryService.getAllEnquiryByCompanyId(user.getCompany().getId(), enquiryStatus, page));
    }

    @GetMapping("/enquiry/all")
    @ResponseBody
    private Object getAllOthersEnquiries(@PathVariable int apiVersion,
                                         Authentication authentication,
                                         @RequestParam(name="text", defaultValue="") String searchText,
                                         @RequestParam(name="page", defaultValue="0") Integer pageNumber,
                                         @RequestParam(name="size", defaultValue="10") Integer size,
                                         @RequestParam(name="enquiryType", defaultValue="Buy") EnquiryType enquiryType) {
        User user = (User)authentication.getPrincipal();
        Pageable page = PageRequest.of(pageNumber, size, Sort.by("lastModifiedDate").descending());
        return ResponseEntity.ok(enquiryService.getAllOthersEnquiryByType(user.getCompany().getId(),
                enquiryType, searchText, page));
    }
}