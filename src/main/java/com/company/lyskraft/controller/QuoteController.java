package com.company.lyskraft.controller;

import com.company.lyskraft.constant.UserRole;
import com.company.lyskraft.entity.Quote;
import com.company.lyskraft.entity.User;
import com.company.lyskraft.constant.EnquiryStatus;
import com.company.lyskraft.service.QuoteService;
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
public class QuoteController {
    private final QuoteService quoteService;

    @PostMapping("/enquiry/{enquiryId}/quote")
    @ResponseBody
    private Object quoteForEnquiry(@PathVariable int apiVersion,
                                   @Valid @RequestBody Quote quoteDetails,
                                   @PathVariable("enquiryId") Long enquiryId,
                                   Authentication authentication) throws Exception {
        User user = (User)authentication.getPrincipal();
        if (!user.getRole().equals(UserRole.ROOT)) {
            quoteDetails.setQuoteCompany(user.getCompany());
        }
        return ResponseEntity.ok(quoteService.createQuote(quoteDetails, enquiryId));
    }

    @GetMapping("/quote")
    @ResponseBody
    private Object getAllQuotesByCurrentUser(@PathVariable int apiVersion,
                                             Authentication authentication,
                                             @RequestParam(name="page", defaultValue="0") Integer pageNumber,
                                             @RequestParam(name="size", defaultValue="10") Integer size,
                                             @RequestParam(name="status", defaultValue="Inreview,Active,Complete") Set<EnquiryStatus> quoteStatus) {
        User user = (User)authentication.getPrincipal();
        Pageable page = PageRequest.of(pageNumber, size, Sort.by("lastModifiedDate").descending());
        return ResponseEntity.ok(quoteService.getAllQuoteByCompanyId(user.getCompany().getId(), quoteStatus, page));
    }

    @GetMapping("/enquiry/{enquiryId}/quote")
    @ResponseBody
    private Object getAllQuotesByEnquiryId(@PathVariable int apiVersion,
                                           Authentication authentication,
                                           @PathVariable("enquiryId") Long enquiryId,
                                           @RequestParam(name="page", defaultValue="0") Integer pageNumber,
                                           @RequestParam(name="size", defaultValue="10") Integer size) throws Exception {
        User user = (User)authentication.getPrincipal();
        Pageable page = PageRequest.of(pageNumber, size, Sort.by("lastModifiedDate").descending());
        return ResponseEntity.ok(quoteService.getAllQuoteByEnquiryId(user, enquiryId, page));
    }

    @PutMapping("/quote/{quoteId}")
    private Object updateQuoteStatus(@PathVariable int apiVersion,
                                     Authentication authentication,
                                     @PathVariable("quoteId") Long quoteId,
                                     @Valid @RequestBody Map<String,EnquiryStatus> quoteStatus) throws Exception {
        User user = (User)authentication.getPrincipal();
        return ResponseEntity.ok(quoteService.updateQuoteStatus(user, quoteId, quoteStatus.get("status")));
    }
}