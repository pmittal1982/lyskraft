package com.company.lyskraft.communication;

import com.twilio.Twilio;
import com.twilio.rest.verify.v2.service.Verification;
import com.twilio.rest.verify.v2.service.VerificationCheck;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class VerifyService {

    @Value("${twilio.account.sid}")
    private String accountSid;
    @Value("${twilio.account.token}")
    private String accountToken;

    @Value("${twilio.account.verify.sid}")
    private String verifySid;

    public String generateOtp(String to, String via) {
        Twilio.init(accountSid, accountToken);
        Verification.creator(
                        verifySid, // this is your verification sid
                        to, //this is your Twilio verified recipient phone number
                        via) // possible value: "sms" or "whatsapp"
                .create();
        return "success";
    }

    public String verifyOtp(String to, String otp) {
        Twilio.init(accountSid, accountToken);
        VerificationCheck.creator(
                        verifySid)
                .setTo(to)
                .setCode(otp)
                .create();
        return "success";
    }
}
