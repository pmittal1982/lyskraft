package com.company.lyskraft.service;

import com.company.lyskraft.communication.EmailService;
import com.company.lyskraft.entity.Company;
import com.company.lyskraft.entity.KycDocument;
import com.company.lyskraft.entity.User;
import com.company.lyskraft.constant.CompanyStatus;
import com.company.lyskraft.constant.UserRole;
import com.company.lyskraft.constant.UserStatus;
import com.company.lyskraft.repository.CompanyRepository;
import com.company.lyskraft.repository.KycDocumentRepository;
import com.company.lyskraft.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class UserService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final UserRepository userRepository;
    private final CompanyRepository companyRepository;
    private final KycDocumentRepository kycDocumentRepository;
    private final JwtService jwtService;
    private final EmailService emailService;
    private final MasterService masterService;

    public String returnAuthToken(String mobileNumber, String deviceToken) {
        Optional<User> user = userRepository.findByMobileNumber(mobileNumber);
        User newUser;
        if(user.isEmpty()) {
            newUser = new User();
            newUser.setMobileNumber(mobileNumber);
            newUser.setDeviceToken(deviceToken);
            newUser.setRole(UserRole.ADMIN);
            newUser.setStatus(UserStatus.Active);
            userRepository.save(newUser);
        } else {
            user.get().setStatus(UserStatus.Active);
            newUser = userRepository.save(user.get());
        }
        return jwtService.generateToken(newUser);
    }

    @CacheEvict(value="mtp_users", key="#userDetails.mobileNumber")
    public User updateUser(User userDetails) {
        companyRepository.save(userDetails.getCompany());
        return userRepository.save(userDetails);
    }

    @CacheEvict(value="mtp_users", key="#userDetails.mobileNumber")
    public User updateCompany(User userDetails, Company companyDetails)
            throws Exception {
        if (userDetails.getCompany() == null) {
            // This is perform KYC case
            companyDetails.setStatus(CompanyStatus.Unverified);
            companyDetails.setLocale(LocaleContextHolder.getLocale());
            if (companyDetails.getKycDocument() != null) {
                companyDetails.setKycDocument(new HashSet<>(kycDocumentRepository.saveAll(companyDetails.getKycDocument())));
            }
            userDetails.setCompany(companyRepository.save(companyDetails));
            userDetails = userRepository.save(userDetails);
            emailService.sendKycEmail(userDetails);
            return userDetails;
        }
        Optional<Company> company = companyRepository.findById(companyDetails.getId());
        if (company.isPresent()) {
            if (userDetails.getCompany().getId() != company.get().getId()) {
                throw new Exception ("Current User is already part of another company");
            }
            //This is when reactivating a deleted account.
            if (company.get().getStatus() == CompanyStatus.Deleted) {
                company.get().setStatus(CompanyStatus.Unverified);
            }
            if (companyDetails.getLocale() != null) {
                company.get().setLocale(companyDetails.getLocale());
            }
            if (companyDetails.getKycDocument() != null) {
                for (KycDocument kycDocument : companyDetails.getKycDocument()) {
                    if (kycDocument.getId() == 0) {
                        kycDocumentRepository.save(kycDocument);
                    }
                }
                company.get().setKycDocument(companyDetails.getKycDocument());
            }
            if (companyDetails.getAddress() != null) {
                company.get().setAddress(companyDetails.getAddress());
            }
            if (companyDetails.getBankAccountNumber() != null) {
                company.get().setBankAccountNumber(companyDetails.getBankAccountNumber());
            }
            if (companyDetails.getBankName() != null) {
                company.get().setBankName(companyDetails.getBankName());
            }
            if (companyDetails.getCompanyNumber() != null) {
                company.get().setCompanyNumber(companyDetails.getCompanyNumber());
            }
            if (companyDetails.getEmail() != null) {
                company.get().setEmail(companyDetails.getEmail());
            }
            if (companyDetails.getCountry() != null) {
                company.get().setCountry(companyDetails.getCountry());
            }
            if (companyDetails.getLegalRepresentativeName() != null) {
                company.get().setLegalRepresentativeName(companyDetails.getLegalRepresentativeName());
            }
            if (companyDetails.getName() != null) {
                company.get().setName(companyDetails.getName());
            }
            if (companyDetails.getPhone() != null) {
                company.get().setPhone(companyDetails.getPhone());
            }
            if (companyDetails.getPinCode() != null) {
                company.get().setPinCode(companyDetails.getPinCode());
            }
            if (companyDetails.getSwiftCode() != null) {
                company.get().setSwiftCode(companyDetails.getSwiftCode());
            }
            companyRepository.save(company.get());
            return userDetails;
        }
        throw new Exception ("Invalid company linked to the user");
    }

    public User addEmployee(Company companyDetails, String mobileNumber) throws Exception {
        Optional<User> user = userRepository.findByMobileNumber(mobileNumber);
        if (user.isPresent()) {
            if (user.get().getStatus().equals(UserStatus.Inactive)) {
                // Associate the user with this company and make it active.
                user.get().setStatus(UserStatus.Active);
                Optional<Company> dbCompany = companyRepository.findById(companyDetails.getId());
                dbCompany.ifPresent(user.get()::setCompany);
                return userRepository.save(user.get());
            } else {
                // Check if User is active with some other company.
                if (user.get().getCompany().getId() != companyDetails.getId()) {
                    throw new Exception("User associated with some other company");
                } else {
                    return user.get();
                }
            }
        } else {
            User newUser = new User();
            newUser.setMobileNumber(mobileNumber);
            newUser.setRole(UserRole.EMPLOYEE);
            newUser.setStatus(UserStatus.Active);
            Optional<Company> dbCompany = companyRepository.findById(companyDetails.getId());
            dbCompany.ifPresent(newUser::setCompany);
            return userRepository.save(newUser);
        }
    }

    public String deleteEmployee(Company companyDetails, String mobileNumber) throws Exception {
        Optional<User> user = userRepository.findByMobileNumberAndStatus(mobileNumber, UserStatus.Active);
        if (user.isPresent() && user.get().getCompany().getId() == companyDetails.getId()) {
            user.get().setStatus(UserStatus.Inactive);
            userRepository.save(user.get());
            return "success";
        }
        throw new Exception("User not found");
    }

    @CacheEvict(value="mtp_users", key="#userDetails.mobileNumber")
    public String deleteAccount(User userDetails) throws Exception {
        // Delete all the users of that Company
        Iterable<User> users = userRepository.findAllByCompanyIdAndStatus(userDetails.getCompany().getId(),
                UserStatus.Active);
        for (User user : users) {
            user.setStatus(UserStatus.Inactive);
        }
        userRepository.saveAll(users);
        // Delete the company
        userDetails.getCompany().setStatus(CompanyStatus.Deleted);
        companyRepository.save(userDetails.getCompany());
        emailService.sendAccountDeleteEmail(userDetails.getCompany());
        return "success";
    }

    public Page<User> getEmployees(Company company, Pageable page) {
        return userRepository.findAllByCompanyIdAndRoleAndStatus(company.getId(),
                UserRole.EMPLOYEE, UserStatus.Active, page);
    }

    public Page<User> pendingVerification(Pageable page) {
        return userRepository.findAllByCompanyIsNullOrCompanyStatus(CompanyStatus.Unverified, page);
    }

    public UserDetailsService userDetailsService() {
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) {
                return masterService.getUserByMobileNumber(username);
            }
        };
    }
}