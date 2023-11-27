package com.company.lyskraft.repository;

import com.company.lyskraft.constant.CompanyStatus;
import com.company.lyskraft.constant.UserRole;
import com.company.lyskraft.entity.User;
import com.company.lyskraft.constant.UserStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByMobileNumberAndStatus(String mobileNumber, UserStatus status);
    Optional<User> findByMobileNumber(String mobileNumber);
    Page<User> findAllByCompanyIsNullOrCompanyStatus(CompanyStatus companyStatus, Pageable page);

    Iterable<User> findAllByCompanyIdAndStatus(long companyId, UserStatus status);

    Page<User> findAllByCompanyIdAndRoleAndStatus(long companyId, UserRole role, UserStatus status, Pageable page);
}