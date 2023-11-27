package com.company.lyskraft.service;

import com.company.lyskraft.communication.EmailService;
import com.company.lyskraft.entity.*;
import com.company.lyskraft.constant.EnquiryStatus;
import com.company.lyskraft.constant.EnquiryType;
import com.company.lyskraft.constant.UserRole;
import com.company.lyskraft.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class EnquiryService {
    private final EnquiryRepository enquiryRepository;
    private final QuoteRepository quoteRepository;
    private final ItemRepository itemRepository;
    private final EmailService emailService;
    private final ChatService chatService;

    public Enquiry createEnquiry(Enquiry enquiryDetails) throws Exception {
        // Fetch the child objects based on Ids provided
        for (Item item : enquiryDetails.getItem()) {
            itemRepository.save(item);
        }
        enquiryDetails.setStatus(EnquiryStatus.Inreview);
        enquiryDetails = enquiryRepository.save(enquiryDetails);
        emailService.sendNewEnquiryEmailToOps(enquiryDetails);
        return enquiryDetails;
    }

    public Enquiry updateEnquiryStatus(User currentUser, long enquiryId, EnquiryStatus enquiryStatus) throws Exception {
        Optional<Enquiry> enquiry = enquiryRepository.findById(enquiryId);
        if (enquiry.isPresent()) {
            if (!currentUser.getRole().equals(UserRole.ROOT) &&
                    currentUser.getCompany().getId() != enquiry.get().getEnquiryCompany().getId()) {
                throw new Exception("Enquiry is not owned by the User");
            }
            if (enquiryStatus == EnquiryStatus.Active && enquiry.get().getStatus() == EnquiryStatus.Expired) {
                enquiry.get().setStatus(EnquiryStatus.Active);
                return enquiryRepository.save(enquiry.get());
            }
            if (enquiryStatus == EnquiryStatus.Closed && enquiry.get().getStatus() == EnquiryStatus.Inreview) {
                enquiry.get().setStatus(EnquiryStatus.Closed);
                return enquiryRepository.save(enquiry.get());
            }
            if ((enquiryStatus == EnquiryStatus.Closed || enquiryStatus == EnquiryStatus.Expired)
                    && enquiry.get().getStatus() == EnquiryStatus.Active) {
                enquiry.get().setStatus(enquiryStatus);
                // Close all active quotes on this enquiry
                closedAllRelatedQuotes(enquiry.get().getId());
                return enquiryRepository.save(enquiry.get());
            }
            throw new Exception(String.format("Enquiry cannot be set to %s as its already %s",
                    enquiryStatus,
                    enquiry.get().getStatus()));
        }
        throw new Exception("Enquiry not present");
    }

    private void closedAllRelatedQuotes(long enquiryId) {
        Set<EnquiryStatus> statuses = new HashSet<>(1);
        statuses.add(EnquiryStatus.Active);
        Iterable<Quote> quotes = quoteRepository.findAllByEnquiryIdAndStatusIn(enquiryId, statuses);
        for (Quote quote : quotes) {
            quote.setStatus(EnquiryStatus.Closed);
            quoteRepository.save(quote);
            // Create chat for all the quote owners to communicate to them.
            chatService.createChatForCloseQuote(quote);
        }
    }
    public Enquiry updateEnquiry(Enquiry enquiryDetails) throws Exception {
        Optional<Enquiry> enquiry = enquiryRepository.findById(enquiryDetails.getId());
        if (enquiry.isPresent()) {
            if (enquiry.get().getStatus() == EnquiryStatus.Closed ||
                    enquiry.get().getStatus() == EnquiryStatus.Complete ||
                    enquiry.get().getStatus() == EnquiryStatus.Expired) {
                throw new Exception("Enquiry is already Complete/Closed/Expired");
            }
            // Fetch the child objects based on Ids provided
            if (enquiryDetails.getItem() != null) {
                for (Item item : enquiryDetails.getItem()) {
                    if (item != null) {
                        Optional<Item> dbItem = itemRepository.findById(item.getId());
                        if (dbItem.isPresent()) {
                            if (item.getProduct() != null) {
                                dbItem.get().setProduct(item.getProduct());
                            }
                            if (!item.getRemarks().isBlank()) {
                                dbItem.get().setRemarks(item.getRemarks());
                            }
                            if (item.getQuantity() != 0) {
                                dbItem.get().setQuantity(item.getQuantity());
                            }
                            itemRepository.save(dbItem.get());
                        }
                    }
                }
            }
            if (enquiryDetails.getStatus() != null) {
                enquiry.get().setStatus(enquiryDetails.getStatus());
            }
            if (enquiryDetails.getPaymentTerms() != null) {
                enquiry.get().setPaymentTerms(enquiryDetails.getPaymentTerms());
            }
            if (enquiryDetails.getTransportationTerms() != null) {
                enquiry.get().setTransportationTerms(enquiryDetails.getTransportationTerms());
            }
            if (enquiryDetails.getOtherTerms() != null) {
                enquiry.get().setOtherTerms(enquiryDetails.getOtherTerms());
            }
            if (enquiryDetails.getOtherAttachmentsUrl() != null) {
                enquiry.get().setOtherAttachmentsUrl(enquiryDetails.getOtherAttachmentsUrl());
            }
            return enquiryRepository.save(enquiry.get());
        }
        throw new Exception("Enquiry not present");
    }

    public Page<Enquiry> getAllEnquiryByCompanyId(long enquiryCompanyId,
                                                  Set<EnquiryStatus> enquiryStatus,
                                                  Pageable page) {
        Page<Enquiry> enquiries = enquiryRepository.findAllByEnquiryCompanyIdAndStatusIn(enquiryCompanyId,
                enquiryStatus,
                page);
        for (Enquiry enquiry : enquiries) {
            if (enquiry.getStatus() != EnquiryStatus.Inreview) {
                enquiry.setQuoteCount(quoteRepository.countByEnquiryIdAndStatusNot(enquiry.getId(),
                        EnquiryStatus.Inreview));
            }
        }
        return enquiries;
    }

    public Page<Enquiry> getAllOthersEnquiryByType(long enquiryCompanyId,
                                                   EnquiryType type,
                                                   String searchText,
                                                   Pageable page) {
        if (searchText.isBlank()) {
            return enquiryRepository.findAllByStatusAndEnquiryTypeAndEnquiryCompanyIdNot(EnquiryStatus.Active,
                    type,
                    enquiryCompanyId,
                    page);
        }
        return enquiryRepository.findAllByStatusAndEnquiryTypeAndEnquiryCompanyIdNotAndItemSkuTitleContainingIgnoreCase(EnquiryStatus.Active,
                type, enquiryCompanyId, searchText, page);
    }

    public Page<Enquiry> getAllEnquiry(Set<EnquiryStatus> enquiryStatus, Pageable page) {
        return enquiryRepository.findAllByStatusIn(enquiryStatus, page);
    }

    // TODO: 19/06/23  Incomplete method to be done later.
    private void getMatchingEnquiries(Iterable<Enquiry> currentList, Iterable<Enquiry> toBeMatchedList) {
        for (Enquiry enquiryFromCurrentList : currentList) {
            Set<Enquiry> matchingEnquiry = new HashSet<>();
            for (Enquiry enquiryFromToBeMatchedList : toBeMatchedList) {
                if (enquiryFromCurrentList.getItem().equals(enquiryFromToBeMatchedList.getItem())) {
                    matchingEnquiry.add(enquiryFromToBeMatchedList);
                }
            }
            enquiryFromCurrentList.setMatchingEnquiries(matchingEnquiry.size());
        }
    }
}