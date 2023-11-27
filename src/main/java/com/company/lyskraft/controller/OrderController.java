package com.company.lyskraft.controller;

import com.company.lyskraft.entity.User;
import com.company.lyskraft.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v{apiVersion}/user")
public class OrderController {

    private final OrderService orderService;

    @GetMapping("/order")
    @ResponseBody
    private Object getAllOrdersByCurrentUser(@PathVariable int apiVersion,
                                             Authentication authentication,
                                             @RequestParam(name="page", defaultValue="0") Integer pageNumber,
                                             @RequestParam(name="size", defaultValue="10") Integer size) {
        User user = (User)authentication.getPrincipal();
        Pageable page = PageRequest.of(pageNumber, size, Sort.by("createdDate").descending());
        return ResponseEntity.ok(orderService.getAllOrdersByCompanyId(user.getCompany().getId(), page));
    }
}
