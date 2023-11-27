package com.company.lyskraft.service;

import com.company.lyskraft.communication.EmailService;
import com.company.lyskraft.entity.*;
import com.company.lyskraft.constant.*;
import com.company.lyskraft.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class QuoteService {

    private final EnquiryRepository enquiryRepository;
    private final QuoteRepository quoteRepository;
    private final ItemRepository itemRepository;
    private final OrderRepository orderRepository;
    private final EmailService emailService;
    private final ChatService chatService;

    public Quote createQuote(Quote quoteDetails, long enquiryId) throws Exception {
        // Fetch the child objects based on Ids provided
        Optional<Enquiry> enquiry = enquiryRepository.findById(enquiryId);
        if (enquiry.isEmpty()) {
            throw new Exception("Invalid Enquiry");
        }
        if (enquiry.get().getStatus() != EnquiryStatus.Active) {
            throw new Exception("This Enquiry is not active");
        }
        if (enquiry.get().getEnquiryCompany().getId() == quoteDetails.getQuoteCompany().getId()) {
            throw new Exception("You cannot Quote for your Enquiry");
        }
        for (Item item : quoteDetails.getItem()) {
            // Setting the ID null so that new items are created for the quote
            item.setId(0);
            itemRepository.save(item);
        }
        quoteDetails.setEnquiry(enquiry.get());
        quoteDetails.setStatus(EnquiryStatus.Inreview);
        quoteDetails = quoteRepository.save(quoteDetails);
        emailService.sendNewQuoteEmailToOps(quoteDetails);
        return quoteDetails;
    }

    public Quote updateQuote(Quote quoteDetails) throws Exception {
        Optional<Quote> quote = quoteRepository.findById(quoteDetails.getId());
        if (quote.isPresent()) {
            if (quote.get().getStatus() == EnquiryStatus.Closed ||
                    quote.get().getStatus() == EnquiryStatus.Complete ||
                    quote.get().getStatus() == EnquiryStatus.Expired) {
                throw new Exception("Quote is already Complete/Closed/Expired");
            }
            // Fetch the child objects based on Ids provided
            if (quoteDetails.getItem() != null) {
                for (Item item : quoteDetails.getItem()) {
                    if (item != null) {
                        Optional<Item> dbItem = itemRepository.findById(item.getId());
                        if (dbItem.isPresent()) {
                            if (item.getPrice() != 0) {
                                dbItem.get().setPrice(item.getPrice());
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
            if (quoteDetails.getPaymentTerms() != null) {
                quote.get().setPaymentTerms(quoteDetails.getPaymentTerms());
            }
            if (quoteDetails.getTransportationTerms() != null) {
                quote.get().setTransportationTerms(quoteDetails.getTransportationTerms());
            }
            if (quoteDetails.getStatus() != null) {
                quote.get().setStatus(quoteDetails.getStatus());
            }
            // Create a chat for the quote
            chatService.createChatForNewQuote(quote.get());
            return quoteRepository.save(quote.get());
        }
        throw new Exception("Quote not present");
    }

    public Page<Quote> getAllQuoteByCompanyId(long biddingCompanyId, Set<EnquiryStatus> quoteStatus, Pageable page) {
        return quoteRepository.findAllByQuoteCompanyIdAndStatusIn(biddingCompanyId, quoteStatus, page);
    }

    public Page<Quote> getAllQuoteByEnquiryId(User currentUser, long enquiryId, Pageable page) throws Exception {
        Optional<Enquiry> enquiry = enquiryRepository.findById(enquiryId);
        if (enquiry.isEmpty()) {
            throw new Exception("Invalid Enquiry");
        }
        if (!currentUser.getRole().equals(UserRole.ROOT) &&
                enquiry.get().getEnquiryCompany().getId() != currentUser.getCompany().getId()) {
            throw new Exception("This Enquiry is not owned by you");
        }
        return quoteRepository.findAllByEnquiryIdAndStatusNot(enquiryId, EnquiryStatus.Inreview, page);
    }

    public Quote updateQuoteStatus(User currentUser, long quoteId, EnquiryStatus quoteStatus) throws Exception {
        Optional<Quote> quote = quoteRepository.findById(quoteId);
        if (quote.isPresent()) {
            if (!currentUser.getRole().equals(UserRole.ROOT) &&
                    quote.get().getQuoteCompany().getId() != currentUser.getCompany().getId() &&
                    quoteStatus != EnquiryStatus.Complete) {
                throw new Exception("This Quote is not owned by you");
            }
            if (quoteStatus == EnquiryStatus.Closed &&
                    (quote.get().getStatus() == EnquiryStatus.Inreview || quote.get().getStatus() == EnquiryStatus.Active)) {
                quote.get().setStatus(EnquiryStatus.Closed);
                quoteRepository.save(quote.get());
                return quote.get();
            }
            if (quoteStatus == EnquiryStatus.Expired && quote.get().getStatus() == EnquiryStatus.Active) {
                quote.get().setStatus(EnquiryStatus.Expired);
                quoteRepository.save(quote.get());
                return quote.get();
            }
            if (quoteStatus == EnquiryStatus.Active && quote.get().getStatus() == EnquiryStatus.Expired) {
                quote.get().setStatus(EnquiryStatus.Active);
                quoteRepository.save(quote.get());
                return quote.get();
            }
            if (quoteStatus == EnquiryStatus.Complete && quote.get().getStatus() == EnquiryStatus.Active) {
                if (!currentUser.getRole().equals(UserRole.ROOT) &&
                        quote.get().getEnquiry().getEnquiryCompany().getId() != currentUser.getCompany().getId()) {
                    throw new Exception("This Enquiry is not owned by you");
                }
                if (quote.get().getEnquiry().getStatus() != EnquiryStatus.Active) {
                    throw new Exception("This Enquiry is not active");
                }
                quote.get().setStatus(EnquiryStatus.Complete);
                quote.get().getEnquiry().setStatus(EnquiryStatus.Complete);
                enquiryRepository.save(quote.get().getEnquiry());
                quoteRepository.save(quote.get());
                Order order = new Order();
                order.setStatus(OrderStatus.Accepted);
                Set<Item> orderItems = new HashSet<>(quote.get().getItem());
                double totalValue = 0;
                for (Item item : quote.get().getItem()) {
                    item = itemRepository.save(item);
                    totalValue = totalValue + item.getPrice();
                    orderItems.add(item);
                }
                order.setTotalValue(totalValue);
                order.setItem(orderItems);
                order.setPaymentTerms(quote.get().getPaymentTerms());
                order.setTransportationTerms(quote.get().getTransportationTerms());
                order.setQuote(quote.get());
                order.setEnquiry(quote.get().getEnquiry());
                orderRepository.save(order);
                emailService.sendSuccessfulOrderEmailToBuyerAndSeller(order);
                chatService.createChatForAcceptQuote(quote.get());
                return quote.get();
            }
            throw new Exception(String.format("Quote cannot be set to %s as its already %s",
                    quoteStatus,
                    quote.get().getStatus()));
        }
        throw new Exception("Quote not present");
    }

    public Page<Quote> getAllQuotes(Pageable page) {
        return quoteRepository.findAll(page);
    }
}