package com.company.lyskraft.service;

import com.company.lyskraft.constant.CompanyStatus;
import com.company.lyskraft.constant.EnquiryStatus;
import com.company.lyskraft.dto.SelectObject;
import com.company.lyskraft.entity.*;
import com.company.lyskraft.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class SelectObjectService {

    private final QuoteRepository quoteRepository;
    private final SkuRepository skuRepository;
    private final EnquiryRepository enquiryRepository;
    private final CompanyRepository companyRepository;
    private final CountryRepository countryRepository;

    public List<SelectObject> getAllQuotesForSelect(long enquiryId) {
        Set<EnquiryStatus> statuses = new HashSet<>();
        statuses.add(EnquiryStatus.Active);
        statuses.add(EnquiryStatus.Inreview);
        Iterable<Quote> quotes = quoteRepository.findAllByEnquiryIdAndStatusIn(enquiryId, statuses);
        List<SelectObject> selectObjects = new ArrayList<>();
        for (Quote quote : quotes) {
            SelectObject selectObject = new SelectObject();
            selectObject.setValue(quote.getId());
            selectObject.setLabel(quote.getUuid());
            selectObjects.add(selectObject);
        }
        return selectObjects;
    }

    @Cacheable("skus_select")
    public List<SelectObject> getAllSkusForSelect() {
        Iterable<Sku> skus = skuRepository.findAll();
        List<SelectObject> selectSkus = new ArrayList<>();
        for (Sku sku : skus) {
            SelectObject selectObject = new SelectObject();
            selectObject.setLabel(sku.getTitle());
            selectObject.setValue(sku.getId());
            selectSkus.add(selectObject);
        }
        return selectSkus;
    }

    public List<SelectObject> getAllEnquiryForSelect() {
        Pageable page = PageRequest.of(0, 99999, Sort.by("lastModifiedDate").descending());
        Set<EnquiryStatus> statuses = new HashSet<>();
        statuses.add(EnquiryStatus.Active);
        statuses.add(EnquiryStatus.Inreview);
        Page<Enquiry> enquiries = enquiryRepository.findAllByStatusIn(statuses, page);
        List<SelectObject> selectObjects = new ArrayList<>();
        for (Enquiry enquiry : enquiries) {
            SelectObject selectObject = new SelectObject();
            selectObject.setValue(enquiry.getId());
            selectObject.setLabel(enquiry.getUuid());
            selectObjects.add(selectObject);
        }
        return selectObjects;
    }

    public List<SelectObject> getAllActiveCompanies(Set<Long> exclude) {
        Set<CompanyStatus> statuses = new HashSet<>();
        statuses.add(CompanyStatus.Verified);
        statuses.add(CompanyStatus.Unverified);
        Iterable<Company> companies = companyRepository.findAllByStatusInAndIdNotIn(statuses, exclude);
        List<SelectObject> selectObjects = new ArrayList<>();
        for (Company company : companies) {
            SelectObject selectObject = new SelectObject();
            selectObject.setValue(company.getId());
            selectObject.setLabel(company.getName());
            selectObjects.add(selectObject);
        }
        return selectObjects;
    }

    @Cacheable("countries_select")
    public List<SelectObject> getAllCountries() {
        Iterable<Country> countries = countryRepository.findAll();
        List<SelectObject> selectObjects = new ArrayList<>();
        for (Country country : countries) {
            SelectObject selectObject = new SelectObject();
            selectObject.setValue(country.getId());
            selectObject.setLabel(country.getName());
            selectObjects.add(selectObject);
        }
        return selectObjects;
    }
}
