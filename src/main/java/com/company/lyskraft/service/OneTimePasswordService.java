package com.company.lyskraft.service;

import com.company.lyskraft.entity.OneTimePassword;
import com.company.lyskraft.helper.OneTimePasswordHelper;
import com.company.lyskraft.repository.OneTimePasswordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class OneTimePasswordService {

    private final OneTimePasswordRepository oneTimePasswordRepository;

    public String returnOneTimePassword(String mobileNumber) {
        OneTimePassword oneTimePassword = new OneTimePassword();
        String otp = OneTimePasswordHelper.createRandomOneTimePassword().get();
        oneTimePassword.setOneTimePasswordCode(otp);
        long expiryInterval = 2L * 60 * 1000;
        oneTimePassword.setExpires(new Date(System.currentTimeMillis() + expiryInterval));
        oneTimePassword.setMobileNumber(mobileNumber);
        oneTimePasswordRepository.save(oneTimePassword);
        return oneTimePassword.getOneTimePasswordCode();
    }

    public String validateOneTimePassword(String mobileNumber, String otp) throws Exception {
        Pageable paging = PageRequest.of(0, 1, Sort.by("createdDate").descending());
        Page<OneTimePassword> oneTimePasswords =
                oneTimePasswordRepository.findByMobileNumberAndOneTimePasswordCode(mobileNumber, otp, paging);
        if (!oneTimePasswords.isEmpty()) {
            OneTimePassword dbOtp = oneTimePasswords.toList().get(0);
            if (dbOtp.getExpires().getTime() >= System.currentTimeMillis()) {
                return dbOtp.getMobileNumber();
            }
        }
        throw new Exception("Invalid OTP");
    }
}