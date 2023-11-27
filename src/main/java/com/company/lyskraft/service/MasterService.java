package com.company.lyskraft.service;

import com.company.lyskraft.entity.Country;
import com.company.lyskraft.entity.User;
import com.company.lyskraft.constant.UserStatus;
import com.company.lyskraft.repository.CountryRepository;
import com.company.lyskraft.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MasterService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final CountryRepository countryRepository;
    private final UserRepository userRepository;

    @Cacheable("countries")
    public Iterable<Country> getAllCountries() {
        return countryRepository.findAll(Sort.by("name").ascending());
    }

    /**
     * This method is here because this needs to be cached and cannot be cached in UserService.
     * @param mobileNumber
     * @return
     */
    @Cacheable("mtp_users")
    public User getUserByMobileNumber(String mobileNumber) {
        logger.info("Getting the data from database for : {}", mobileNumber);
        return userRepository.findByMobileNumberAndStatus(mobileNumber, UserStatus.Active)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}