package com.company.lyskraft.controller;

import com.company.lyskraft.constant.UserRole;
import com.company.lyskraft.entity.Company;
import com.company.lyskraft.entity.User;
import com.company.lyskraft.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v{apiVersion}/user")
public class UserController {

    private final UserService userService;

    @GetMapping
    @ResponseBody
    private Object getUser(@PathVariable int apiVersion,
                           Authentication authentication) {
        return ResponseEntity.ok((User)authentication.getPrincipal());
    }

    @PostMapping
    @ResponseBody
    private Object updateCompany(@PathVariable int apiVersion,
                                 Authentication authentication,
                                 @Valid @RequestBody Company companyDetails) throws Exception {
        User user = (User)authentication.getPrincipal();
        return ResponseEntity.ok(userService.updateCompany(user, companyDetails));
    }

    @PostMapping("/employee")
    @ResponseBody
    private Object addEmployee(@PathVariable int apiVersion,
                               Authentication authentication,
                               @Valid @RequestBody Map<String, String> mobileNumber) throws Exception {
        User user = (User)authentication.getPrincipal();
        return ResponseEntity.ok(userService.addEmployee(user.getCompany(), mobileNumber.get("mobileNumber")));
    }

    @DeleteMapping("/employee")
    @ResponseBody
    private Object deleteEmployee(@PathVariable int apiVersion,
                                  Authentication authentication,
                                  @Valid @RequestBody Map<String, String> mobileNumber) throws Exception {
        User user = (User)authentication.getPrincipal();
        return ResponseEntity.ok(userService.deleteEmployee(user.getCompany(), mobileNumber.get("mobileNumber")));
    }

    @DeleteMapping("/account")
    @ResponseBody
    private Object deleteAccount(@PathVariable int apiVersion,
                                 Authentication authentication) throws Exception {
        User user = (User)authentication.getPrincipal();
        if (user.getRole() != UserRole.ADMIN && user.getRole() != UserRole.ROOT) {
            throw new Exception("User not allowed to delete the account");
        }
        return ResponseEntity.ok(userService.deleteAccount(user));
    }

    @GetMapping("/employee")
    @ResponseBody
    private Object getEmployees(@PathVariable int apiVersion,
                                Authentication authentication,
                                @RequestParam(name="page", defaultValue="0") Integer pageNumber,
                                @RequestParam(name="size", defaultValue="10") Integer size) {
        User user = (User)authentication.getPrincipal();
        Pageable page = PageRequest.of(pageNumber, size, Sort.by("lastModifiedDate").descending());
        return ResponseEntity.ok(userService.getEmployees(user.getCompany(), page));
    }
}