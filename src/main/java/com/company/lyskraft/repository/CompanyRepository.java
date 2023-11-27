package com.company.lyskraft.repository;

import com.company.lyskraft.constant.CompanyStatus;
import com.company.lyskraft.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Set;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {
    Iterable<Company> findAllBySellInterestIdInAndStatusNot(Collection<Long> skuIds, CompanyStatus companyStatus);
    Iterable<Company> findAllByBuyInterestIdInAndStatusNot(Collection<Long> skuIds, CompanyStatus companyStatus);

    Iterable<Company> findAllByKycDocumentImageUrl(String imageUrl);

    Iterable<Company> findAllByStatusInAndIdNotIn(Set<CompanyStatus> companyStatus, Set<Long> exclude);
}