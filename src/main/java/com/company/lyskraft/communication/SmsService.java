package com.company.lyskraft.communication;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

@Service
@EnableAsync
public class SmsService {

    @Value("${twilio.account.sid}")
    private String accountSid;
    @Value("${twilio.account.token}")
    private String accountToken;
    @Value("${twilio.account.phoneNumber}")
    private String fromPhoneNumber;
    @Async
    public void sendOtpSms(String to, String otp) {
        String text = otp +" is your MetalTrade verification code.";
        Twilio.init(accountSid, accountToken);
        Message.creator(new PhoneNumber(to), new PhoneNumber(fromPhoneNumber), text).create();
    }
}