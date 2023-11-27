package com.company.lyskraft.repository;

import com.company.lyskraft.entity.Quote;
import com.company.lyskraft.constant.EnquiryStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface QuoteRepository extends JpaRepository<Quote, Long> {
    Page<Quote> findAllByQuoteCompanyIdAndStatusIn(long quoteCompanyId, Set<EnquiryStatus> quoteStatus, Pageable page);
    Page<Quote> findAllByEnquiryIdAndStatusNot(long enquiryId, EnquiryStatus status, Pageable page);
    int countByEnquiryIdAndStatusNot(long requestForQuoteId, EnquiryStatus status);
    Iterable<Quote> findAllByEnquiryIdAndStatusIn(long enquiryId, Set<EnquiryStatus> quoteStatus);
}