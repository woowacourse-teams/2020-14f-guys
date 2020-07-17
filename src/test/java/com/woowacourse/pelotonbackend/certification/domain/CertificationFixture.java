package com.woowacourse.pelotonbackend.certification.domain;

import java.util.Base64;

import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import com.woowacourse.pelotonbackend.certification.domain.dto.CertificationCreateRequest;
import com.woowacourse.pelotonbackend.vo.ImageUrl;

public class CertificationFixture {
    public static final CertificationStatus TEST_STATUS = CertificationStatus.SUCCESS;
    public static final String TEST_DESCRIPTION = "좋은 인증이다..";
    public static final Long TEST_RIDER_ID = 1L;
    public static final Long TEST_MISSION_ID = 1L;
    public static final Long TEST_CERTIFICATION_ID = 1L;
    public static final byte[] TEST_FILE = Base64.getEncoder().encode("Sample File".getBytes());
    public static final String TEST_FILE_NAME = "SampleFile.jpeg";
    public static final ImageUrl TEST_FILE_URL = new ImageUrl("https://pbs.twimg.com/media/DeCmgVAUwAYOc-W.jpg");
    public static final String TEST_MULTIPART_NAME = "certification_image";
    public static final String EXPECTED_URL = "https://i.ytimg.com/vi/Bt-T02tXTvg/maxresdefault.jpg";

    public static Certification createWithoutId() {
        return Certification.builder()
            .status(TEST_STATUS)
            .description(TEST_DESCRIPTION)
            .riderId(AggregateReference.to(TEST_RIDER_ID))
            .missionId(AggregateReference.to(TEST_MISSION_ID))
            .image(TEST_FILE_URL)
            .build();
    }

    public static Certification createWithId() {
        return Certification.builder()
            .id(TEST_CERTIFICATION_ID)
            .status(TEST_STATUS)
            .description(TEST_DESCRIPTION)
            .riderId(AggregateReference.to(TEST_RIDER_ID))
            .missionId(AggregateReference.to(TEST_MISSION_ID))
            .image(TEST_FILE_URL)
            .build();
    }

    public static CertificationCreateRequest createMockRequest() {
        return CertificationCreateRequest.builder()
            .status(TEST_STATUS)
            .description(TEST_DESCRIPTION)
            .build();
    }

    public static CertificationCreateRequest createBadMockRequest() {
        return CertificationCreateRequest.builder()
            .description(TEST_DESCRIPTION)
            .build();
    }

    public static MultipartFile createMockMultipartFile() {
        return new MockMultipartFile(TEST_MULTIPART_NAME, TEST_FILE_NAME, MediaType.IMAGE_JPEG_VALUE,
            TEST_FILE);
    }
}
