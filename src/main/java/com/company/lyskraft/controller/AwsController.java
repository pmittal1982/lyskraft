package com.company.lyskraft.controller;

import com.company.lyskraft.service.AwsService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v{apiVersion}/user")
public class AwsController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final AwsService awsService;
    @PostMapping("/file/upload")
    public Object save(@PathVariable int apiVersion,
                       @RequestParam("file") MultipartFile multipartFile) throws Exception {
        if(multipartFile.getSize() > 5000000) {
            throw new Exception("File size should be less than 5MB");
        }
        String extension = FilenameUtils.getExtension(multipartFile.getOriginalFilename());
        String imageUrl = awsService.save(multipartFile);
        return ResponseEntity.ok(imageUrl);
    }

    @DeleteMapping("/file/aws")
    public Object deleteUnmappedFiles(@PathVariable int apiVersion) {
        awsService.deleteUnlinkedImages();
        return ResponseEntity.ok("success");
    }
}