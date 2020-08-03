package com.woowacourse.pelotonbackend.common.upload;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.woowacourse.pelotonbackend.common.exception.UploadFailureException;

@Component
public class S3UploadService implements UploadService {
    private AmazonS3 amazonS3;

    @Value("${cloud.aws.region.static}")
    private String region;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @PostConstruct
    public void S3UploadService() {
        amazonS3 = AmazonS3ClientBuilder.standard()
            .withRegion(region)
            .build();
    }

    public String upload(final MultipartFile file) {
        final String fileName = file.getOriginalFilename();

        try {
            amazonS3.putObject(new PutObjectRequest(bucket, fileName, file.getInputStream(), null)
                .withCannedAcl(CannedAccessControlList.PublicRead));
        } catch (Exception e) {
            throw new UploadFailureException();
        }

        return amazonS3.getUrl(bucket, fileName).toString();
    }
}
