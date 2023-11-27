package com.company.lyskraft.communication;

import com.company.lyskraft.constant.CompanyStatus;
import com.company.lyskraft.entity.*;
import com.company.lyskraft.constant.EnquiryType;
import com.company.lyskraft.repository.CompanyRepository;
import freemarker.template.Configuration;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

import java.io.StringWriter;
import java.util.*;

@Service
@EnableAsync
@RequiredArgsConstructor
public class EmailService {
    private final Configuration configuration;
    private final JavaMailSender javaMailSender;
    private final CompanyRepository companyRepository;
    @Value("${spring.mail.username}")
    private String fromEmail;
    @Value("${ops.email.address}")
    private String opsEmailAddress;

    @Async
    public void sendKycEmail(User user) throws Exception {
        Map<String, Object> model = new HashMap<>();
        model.put("user", user);
        sendEmail("KYC Complete: Welcome To MetalTrade.io",
                user.getCompany().getEmail(),
                model,
                "kycComplete.ftlh",
                user.getCompany().getLocale());
    }

    @Async
    public void sendAccountDeleteEmail(Company company) throws Exception {
        Map<String, Object> model = new HashMap<>();
        model.put("user", company);
        sendEmail("Account Deleted on MetalTrade.io",
                company.getEmail(),
                model,
                "deleteAccount.ftlh",
                company.getLocale());
    }

    @Async
    public void sendNewEnquiryEmailToOps(Enquiry enquiry) throws Exception {
        Map<String, Object> model = new HashMap<>();
        model.put("enquiry", enquiry);
        sendEmail("New Enquiry: Validation required",
                opsEmailAddress,
                model,
                "newEnquiry.ftlh",
                Locale.ENGLISH);
    }

    @Async
    public void broadcastNewEnquiryEmailToBuyersOrSellers(Enquiry enquiry) throws Exception {
        Collection<Long> skus = new HashSet<Long>(enquiry.getItem().size());
        Map<String, Object> model;
        Iterable<Company> companies;
        for (Item item : enquiry.getItem()) {
            skus.add(item.getSku().getId());
        }
        if (enquiry.getEnquiryType() == EnquiryType.Buy) {
            companies = companyRepository.findAllBySellInterestIdInAndStatusNot(skus, CompanyStatus.Deleted);
        } else {
            companies = companyRepository.findAllByBuyInterestIdInAndStatusNot(skus, CompanyStatus.Deleted);
        }
        for (Company company : companies) {
            model = new HashMap<>();
            model.put("enquiry", enquiry);
            model.put("company", company);
            sendEmail("New Enquiry: Submit quote",
                    company.getEmail(),
                    model,
                    "broadcastEnquiry.ftlh",
                    company.getLocale());
        }
    }

    @Async
    public void sendNewQuoteEmailToOps(Quote quote) throws Exception {
        Map<String, Object> model = new HashMap<>();
        model.put("quote", quote);
        sendEmail("New Quote: Validation required",
                opsEmailAddress,
                model,
                "newQuote.ftlh",
                Locale.ENGLISH);
    }

    @Async
    public void publishNewQuoteToBuyerOrSeller(Quote quote) throws Exception {
        Map<String, Object> model = new HashMap<>();
        model.put("quote", quote);
        sendEmail("Congratulations: Received new Quote on your Enquiry",
                quote.getEnquiry().getEnquiryCompany().getEmail(),
                model,
                "publishQuote.ftlh",
                quote.getEnquiry().getEnquiryCompany().getLocale());
    }

    @Async
    public void sendSuccessfulOrderEmailToBuyerAndSeller(Order order) throws Exception {
        Map<String, Object> model = new HashMap<>();
        model.put("order", order);
        model.put("items", order.getItem());
        sendEmail("Congratulations: Your quote accepted and Order created",
                order.getQuote().getQuoteCompany().getEmail(),
                model,
                "acceptQuote.ftlh",
                order.getQuote().getQuoteCompany().getLocale());
        sendEmail("Order created successfully",
                order.getEnquiry().getEnquiryCompany().getEmail(),
                model,
                "successfulOrder.ftlh",
                order.getEnquiry().getEnquiryCompany().getLocale());

    }
    private String getEmailContent(Map<String, Object> objects, String template, Locale locale) throws Exception {
        StringWriter stringWriter = new StringWriter();
        configuration.getTemplate(template, locale).process(objects, stringWriter);
        return stringWriter.getBuffer().toString();
    }

    private void sendEmail(String subject, String to, Map<String, Object> object, String template, Locale locale)
            throws Exception {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
        helper.setFrom(fromEmail, "MetalTrade.io");
        helper.setSubject(subject);
        helper.setTo(to);
        String emailContent = getEmailContent(object, template, locale);
        helper.setText(emailContent, true);
        javaMailSender.send(mimeMessage);
    }
}