package com.company.lyskraft.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import com.company.lyskraft.entity.ChatBody;
import com.company.lyskraft.entity.Company;
import com.company.lyskraft.entity.Enquiry;
import com.company.lyskraft.repository.ChatBodyRepository;
import com.company.lyskraft.repository.CompanyRepository;
import com.company.lyskraft.repository.EnquiryRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.nio.file.Files;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AwsService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final AmazonS3 amazonS3;
    private final ChatBodyRepository chatBodyRepository;
    private final EnquiryRepository enquiryRepository;
    private final CompanyRepository companyRepository;

    @Value("${s3.bucket.name}")
    private String s3BucketName;

    private File convertMultiPartFileToFile(final MultipartFile multipartFile) throws Exception {
        File file = new File(multipartFile.getOriginalFilename());
        FileOutputStream outputStream = new FileOutputStream(file);
        outputStream.write(multipartFile.getBytes());
        return file;
    }

    public S3ObjectInputStream findByName(String fileName) {
        return amazonS3.getObject(s3BucketName, fileName).getObjectContent();
    }

    public String save(final MultipartFile multipartFile) throws Exception {
        final File file = convertMultiPartFileToFile(multipartFile);
        final String fileName = LocalDateTime.now() + "_" + file.getName();
        logger.info("Uploading file with name {}", fileName);
        final PutObjectRequest putObjectRequest = new PutObjectRequest(s3BucketName, fileName, file);
        putObjectRequest.setCannedAcl(CannedAccessControlList.PublicRead);
        amazonS3.putObject(putObjectRequest);
        URL s3Url = amazonS3.getUrl(s3BucketName, fileName);
        String cdnUrl = prepareUrl(fileName);
        logger.info("The S3 URL is : {}", s3Url.toExternalForm());
        logger.info("The CDN URL is : {}", cdnUrl);
        Files.delete(file.toPath()); // Remove the file locally created in the project folder
        return cdnUrl;
    }


    @Profile("production")
    @Scheduled(cron = "0 0 1 * * MON-FRI", zone = "Asia/Shanghai")
    public void deleteUnlinkedImages() {
        ObjectListing list = amazonS3.listObjects(s3BucketName);
        while (true) {
            for (S3ObjectSummary sum : list.getObjectSummaries()) {

                logger.info("The file name is : {} last modified is {}", sum.getKey(), sum.getLastModified());
                if (!checkIfFileUsed(sum.getKey())) {
                    logger.info("Deleting file with name : {}", sum.getKey());
                    amazonS3.deleteObject(s3BucketName, sum.getKey());
                }
            }
            if (list.isTruncated()) {
                logger.info("Getting more elements......");
                list = amazonS3.listNextBatchOfObjects(list);
            } else {
                break;
            }
        }
    }

    private String prepareUrl(String fileName) {
        return "https://d2p8hf7x0fchj5.cloudfront.net/"+fileName;
    }

    private boolean checkIfFileUsed(String fileName) {
        // Check in KYC table
        Iterable<Company> companies = companyRepository.findAllByKycDocumentImageUrl(prepareUrl(fileName));
        for (Company company : companies) {
            return true;
        }
        // Check in Enquiry table
        Iterable<Enquiry> enquiries = enquiryRepository.findAllByOtherAttachmentsUrl(prepareUrl(fileName));
        for (Enquiry enquiry : enquiries) {
            return true;
        }
        // Check in chat table
        Iterable<ChatBody> chatBodies = chatBodyRepository.findAllByAttachmentUrl(prepareUrl(fileName));
        for (ChatBody chatBody : chatBodies) {
            return true;
        }
        return false;
    }
}