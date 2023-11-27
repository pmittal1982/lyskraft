package com.company.lyskraft.repository;

import com.company.lyskraft.entity.Enquiry;
import com.company.lyskraft.constant.EnquiryStatus;
import com.company.lyskraft.constant.EnquiryType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface EnquiryRepository extends JpaRepository<Enquiry, Long> {

    Iterable<Enquiry> findAllByOtherAttachmentsUrl(String otherAttachmentsUrl);
    Page<Enquiry> findAllByEnquiryCompanyIdAndStatusIn(long enquiryCompanyId,
                                                       Set<EnquiryStatus> enquiryStatus,
                                                       Pageable page);
    Page<Enquiry> findAllByStatusAndEnquiryTypeAndEnquiryCompanyIdNot(EnquiryStatus enquiryStatus,
                                                                      EnquiryType type,
                                                                      long enquiryCompanyId,
                                                                      Pageable page);
    Page<Enquiry> findAllByStatusIn(Set<EnquiryStatus> status, Pageable page);
    Page<Enquiry> findAllByStatusAndEnquiryTypeAndEnquiryCompanyIdNotAndItemSkuTitleContainingIgnoreCase(EnquiryStatus enquiryStatus,
                                                                                                         EnquiryType type,
                                                                                                         long enquiryCompanyId,
                                                                                                         String text,
                                                                                                         Pageable page);
}