package com.woowacourse.pelotonbackend.infra.upload;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.woowacourse.pelotonbackend.common.exception.UploadFailureException;
import com.woowacourse.pelotonbackend.support.RandomGenerator;
import liquibase.util.file.FilenameUtils;

@Component
public class S3UploadService implements UploadService {
    @Autowired
    private RandomGenerator randomGenerator;
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

    public String uploadImage(final MultipartFile file, final String path) {
        String fileName = randomGenerator.getRandomSHA256(file.getOriginalFilename());
        final String key = String.format("%s%s.%s", path, fileName, FilenameUtils.getExtension(file.getOriginalFilename()));

        try {
            requestToUpload(file, key);
        } catch (SdkClientException | IOException e) {
            e.printStackTrace();
            throw new UploadFailureException(e);
        }

        return amazonS3.getUrl(bucket, key).toString();
    }

    private void requestToUpload(final MultipartFile file, final String key) throws IOException {
        final ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(file.getSize());
        amazonS3.putObject(new PutObjectRequest(bucket, key, file.getInputStream(), metadata)
            .withCannedAcl(CannedAccessControlList.PublicRead));
    }
}
