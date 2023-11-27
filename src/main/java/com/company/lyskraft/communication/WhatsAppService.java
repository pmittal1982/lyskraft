package com.company.lyskraft.communication;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

@Service
@EnableAsync
public class WhatsAppService {
    @Value("${twilio.account.sid}")
    private String accountSid;
    @Value("${twilio.account.token}")
    private String accountToken;

    @Value("${twilio.account.waNumber}")
    private String fromWaNumber;

    @Async
    public void sendOtpWA(String to, String otp) {
        Twilio.init(accountSid, accountToken);
        String text = otp +" is your MetalTrade verification code.";
        Message message = Message.creator(
                new com.twilio.type.PhoneNumber("whatsapp:"+to),
                new com.twilio.type.PhoneNumber("whatsapp:"+fromWaNumber),
                text).create();
    }
}
