package com.company.lyskraft.controller;

import com.company.lyskraft.constant.CallbackStatus;
import com.company.lyskraft.entity.CallbackRequest;
import com.company.lyskraft.service.CallbackRequestService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v{apiVersion}/callback")
public class CallbackRequestController {

    private final CallbackRequestService callbackRequestService;
    @PostMapping
    @ResponseBody
    private Object createCallbackRequest(@PathVariable int apiVersion,
                                         @Valid @RequestBody CallbackRequest callbackRequest) {
        callbackRequest.setStatus(CallbackStatus.Unattended);
        return ResponseEntity.ok(callbackRequestService.createCallbackRequest(callbackRequest));
    }
}