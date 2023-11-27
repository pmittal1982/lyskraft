package com.company.lyskraft.service;

import com.company.lyskraft.entity.CallbackRequest;
import com.company.lyskraft.repository.CallbackRequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CallbackRequestService {

    private final CallbackRequestRepository callbackRequestRepository;
    public CallbackRequest createCallbackRequest(CallbackRequest callbackRequest) {
        return callbackRequestRepository.save(callbackRequest);
    }
}