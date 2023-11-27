package com.company.lyskraft.controller;

import com.company.lyskraft.constant.EnquiryStatus;
import com.company.lyskraft.entity.*;
import com.company.lyskraft.service.*;
import com.company.lyskraft.scrap.StocksScrapperService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.CacheManager;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v{apiVersion}/admin")
public class BackOfficeController {

    private final CSVService csvHelper;
    private final EnquiryService enquiryService;
    private final QuoteService quoteService;
    private final UserService userService;
    private final OrderService orderService;
    private final StocksScrapperService stocksScrapperService;
    private final CacheManager cacheManager;
    private final ChatService chatService;

    @PostMapping("/csv/upload")
    @ResponseBody
    public Object uploadFile(@PathVariable int apiVersion,
                             @RequestParam("file") MultipartFile file) throws Exception {
        if (csvHelper.hasCSVFormat(file)) {
            csvHelper.uploadData(file.getInputStream());
            return ResponseEntity.ok("success");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/scrap")
    @ResponseBody
    public Object scrapStock(@PathVariable int apiVersion) throws Exception {
        stocksScrapperService.scrapIndex();
        return ResponseEntity.ok("success");
    }

    @GetMapping("/clear/cache")
    public void clearAllCaches(@PathVariable int apiVersion) {
        cacheManager.getCacheNames().forEach(cacheName -> Objects.requireNonNull(cacheManager.getCache(cacheName)).clear());
    }

    @PutMapping("/quote")
    @ResponseBody
    private Object updateQuoteForEnquiry(@PathVariable int apiVersion,
                                         @Valid @RequestBody Quote quoteDetails) throws Exception {
        return ResponseEntity.ok(quoteService.updateQuote(quoteDetails));
    }

    @GetMapping("/quote/all")
    @ResponseBody
    private Object getAllQuotes(@PathVariable int apiVersion,
                                @RequestParam(name="page", defaultValue="0") Integer pageNumber,
                                @RequestParam(name="size", defaultValue="10") Integer size) {
        Pageable page = PageRequest.of(pageNumber, size, Sort.by("lastModifiedDate").descending());
        return ResponseEntity.ok(quoteService.getAllQuotes(page));
    }

    @PutMapping("/enquiry")
    @ResponseBody
    private Object updateEnquiry(@PathVariable int apiVersion,
                                 @Valid @RequestBody Enquiry enquiryDetails) throws Exception {
        return ResponseEntity.ok(enquiryService.updateEnquiry(enquiryDetails));
    }

    @GetMapping("/enquiry/all")
    @ResponseBody
    private Object getAllEnquiries(@PathVariable int apiVersion,
                                   @RequestParam(name="page", defaultValue="0") Integer pageNumber,
                                   @RequestParam(name="size", defaultValue="10") Integer size,
                                   @RequestParam(name="status", defaultValue="Inreview,Active,Complete,Closed,Expired") Set<EnquiryStatus> enquiryStatus) {
        Pageable page = PageRequest.of(pageNumber, size, Sort.by("lastModifiedDate").descending());
        return ResponseEntity.ok(enquiryService.getAllEnquiry(enquiryStatus, page));
    }

    @PutMapping("/order")
    @ResponseBody
    private Object updateOrder(@PathVariable int apiVersion,
                               @Valid @RequestBody Order orderDetails) throws Exception {
        return ResponseEntity.ok(orderService.updateOrder(orderDetails));
    }

    @GetMapping("/order/all")
    @ResponseBody
    private Object getAllOrders(@PathVariable int apiVersion,
                                @RequestParam(name="page", defaultValue="0") Integer pageNumber,
                                @RequestParam(name="size", defaultValue="10") Integer size) {
        Pageable page = PageRequest.of(pageNumber, size, Sort.by("lastModifiedDate").descending());
        return ResponseEntity.ok(orderService.getAllOrders(page));
    }

    @GetMapping("/pending/verification")
    @ResponseBody
    private Object getPendingVerification(@PathVariable int apiVersion,
                                          @RequestParam(name="page", defaultValue="0") Integer pageNumber,
                                          @RequestParam(name="size", defaultValue="10") Integer size) {
        Pageable page = PageRequest.of(pageNumber, size, Sort.by("lastModifiedDate").descending());
        return ResponseEntity.ok(userService.pendingVerification(page));
    }

    @PostMapping("/chat")
    @ResponseBody
    private Object createAdminChat(@PathVariable int apiVersion,
                                   @Valid @RequestBody ChatMessage chatMessage) throws Exception {
        chatService.saveChatMessage(chatMessage);
        return ResponseEntity.ok("Success");
    }

    @PutMapping("/user")
    @ResponseBody
    private Object updateUser(@PathVariable int apiVersion,
                              @Valid @RequestBody User userDetails) {
        return ResponseEntity.ok(userService.updateUser(userDetails));
    }
}