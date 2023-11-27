package com.company.lyskraft.controller;

import com.company.lyskraft.communication.VerifyService;
import com.company.lyskraft.service.OneTimePasswordService;
import com.company.lyskraft.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v{apiVersion}/auth")
public class VerifyController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final UserService userService;
    private final VerifyService verifyService;
    private final OneTimePasswordService oneTimePasswordService;

    @PostMapping("/generate")
    @ResponseBody
    private Object getOneTimePassword(@PathVariable int apiVersion,
                                      @Valid @RequestBody Map<String, String> token) {
        // This is hardcoded for the testing account shared to google
        if ("+911234567890".equals(token.get("mobileNumber"))) {
            return ResponseEntity.ok("1234");
        }
        if(apiVersion == 1) {
            return ResponseEntity.ok(oneTimePasswordService.returnOneTimePassword(token.get("mobileNumber")));
        }
        return ResponseEntity.ok(verifyService.generateOtp(token.get("mobileNumber"), token.get("via")));
    }

    @PostMapping("/validate")
    @ResponseBody
    private Object validateOneTimePassword(@PathVariable int apiVersion,
                                           @Valid @RequestBody Map<String, String> token) throws Exception {
        // This is hardcoded for the testing account shared to google
        if ("+911234567890".equals(token.get("mobileNumber"))) {
            return ResponseEntity.ok(userService.returnAuthToken("+911234567890", token.get("deviceToken")));
        }
        if(apiVersion == 1) {
            String mobileNumber =
                    oneTimePasswordService.validateOneTimePassword(token.get("mobileNumber"), token.get("otp"));
            if (mobileNumber != null) {
                return ResponseEntity.ok(userService.returnAuthToken(mobileNumber, token.get("deviceToken")));
            }
            return ResponseEntity.status(HttpStatus.FORBIDDEN);
        }

        String result = verifyService.verifyOtp(token.get("mobileNumber"), token.get("otp"));
        if(result.equals("success")) {
            return ResponseEntity.ok(userService.returnAuthToken(token.get("mobileNumber"), token.get("deviceToken")));
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN);
    }
}