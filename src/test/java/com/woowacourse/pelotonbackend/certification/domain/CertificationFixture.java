package com.woowacourse.pelotonbackend.certification.domain;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

import com.woowacourse.pelotonbackend.certification.presentation.dto.CertificationDescriptionUpdateRequest;
import com.woowacourse.pelotonbackend.certification.presentation.dto.CertificationRequest;
import com.woowacourse.pelotonbackend.certification.presentation.dto.CertificationResponse;
import com.woowacourse.pelotonbackend.certification.presentation.dto.CertificationResponses;
import com.woowacourse.pelotonbackend.certification.presentation.dto.CertificationStatusUpdateRequest;
import com.woowacourse.pelotonbackend.vo.ImageUrl;

public class CertificationFixture {
    public static final CertificationStatus TEST_CERTIFICATION_STATUS = CertificationStatus.SUCCESS;
    public static final CertificationStatus TEST_UPDATED_CERTIFICATION_STATUS = CertificationStatus.REPORTED;
    public static final String TEST_CERTIFICATION_DESCRIPTION = "좋은 인증이다..";
    public static final String TEST_UPDATED_CERTIFICATION_DESCRIPTION = "과연.. 좋은 인증..일까아?";
    public static final Long TEST_RIDER_ID = 1L;
    public static final Long TEST_UPDATED_RIDER_ID = 2L;
    public static final Long TEST_MISSION_ID = 1L;
    public static final Long TEST_UPDATED_MISSION_ID = 2L;
    public static final Long TEST_CERTIFICATION_ID = 1L;
    public static final byte[] TEST_CERTIFICATION_FILE = Base64.getEncoder().encode("Sample File".getBytes());
    public static final byte[] TEST_UPDATED_CERTIFICATION_FILE = Base64.getEncoder()
        .encode("Sample File Updated".getBytes());
    public static final String TEST_CERTIFICATION_FILE_NAME = "SampleFile.jpeg";
    public static final String TEST_UPDATED_CERTIFICATION_FILE_NAME = "SampleFileUpdated.jpeg";
    public static final ImageUrl TEST_CERTIFICATION_FILE_URL = new ImageUrl(
        "https://pbs.twimg.com/media/DeCmgVAUwAYOc-W.jpg");
    public static final ImageUrl TEST_UPDATED_CERTIFICATION_FILE_URL = new ImageUrl(
        "https://lh3.googleusercontent.com/proxy/bAAFnEewpHmJqBSSHxSht7ZqN4tR.jpg"
    );
    public static final String TEST_CERTIFICATION_MULTIPART_NAME = "certification_image";
    public static final LocalDateTime TEST_CREATED_AT = LocalDateTime.parse(LocalDateTime.now()
        .format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS")));

    public static Certification createCertificationWithoutId() {
        return Certification.builder()
            .status(TEST_CERTIFICATION_STATUS)
            .description(TEST_CERTIFICATION_DESCRIPTION)
            .riderId(AggregateReference.to(TEST_RIDER_ID))
            .missionId(AggregateReference.to(TEST_MISSION_ID))
            .image(TEST_CERTIFICATION_FILE_URL)
            .build();
    }

    public static Certification createCertificationWithId() {
        return Certification.builder()
            .id(TEST_CERTIFICATION_ID)
            .status(TEST_CERTIFICATION_STATUS)
            .description(TEST_CERTIFICATION_DESCRIPTION)
            .riderId(AggregateReference.to(TEST_RIDER_ID))
            .missionId(AggregateReference.to(TEST_MISSION_ID))
            .image(TEST_CERTIFICATION_FILE_URL)
            .createdAt(TEST_CREATED_AT)
            .build();
    }

    public static Certification updatedCertification() {
        return Certification.builder()
            .id(TEST_CERTIFICATION_ID)
            .status(TEST_UPDATED_CERTIFICATION_STATUS)
            .description(TEST_UPDATED_CERTIFICATION_DESCRIPTION)
            .riderId(AggregateReference.to(TEST_UPDATED_RIDER_ID))
            .missionId(AggregateReference.to(TEST_UPDATED_MISSION_ID))
            .image(TEST_UPDATED_CERTIFICATION_FILE_URL)
            .build();
    }

    public static Certification createDescriptionUpdatedCertification() {
        return createCertificationWithId().toBuilder()
            .description(TEST_UPDATED_CERTIFICATION_DESCRIPTION)
            .build();
    }

    public static Certification createStatusUpdatedCertification() {
        return createCertificationWithId().toBuilder()
            .status(TEST_UPDATED_CERTIFICATION_STATUS)
            .build();
    }

    public static CertificationDescriptionUpdateRequest createDescriptionUpdateRequest() {
        return CertificationDescriptionUpdateRequest.builder()
            .description(TEST_UPDATED_CERTIFICATION_DESCRIPTION)
            .build();
    }

    public static CertificationStatusUpdateRequest createStatusUpdateRequest() {
        return CertificationStatusUpdateRequest.builder()
            .status(TEST_UPDATED_CERTIFICATION_STATUS)
            .build();
    }

    public static CertificationRequest createMockCertificationRequest() {
        return CertificationRequest.builder()
            .status(TEST_CERTIFICATION_STATUS)
            .description(TEST_CERTIFICATION_DESCRIPTION)
            .riderId(TEST_RIDER_ID)
            .missionId(TEST_MISSION_ID)
            .build();
    }

    public static CertificationRequest createBadMockCertificationRequest() {
        return CertificationRequest.builder()
            .status(TEST_CERTIFICATION_STATUS)
            .description(TEST_CERTIFICATION_DESCRIPTION)
            .riderId(TEST_RIDER_ID)
            .build();
    }

    public static MockMultipartFile createMockCertificationMultipartFile() {
        return new MockMultipartFile(TEST_CERTIFICATION_MULTIPART_NAME, TEST_CERTIFICATION_FILE_NAME,
            MediaType.IMAGE_JPEG_VALUE, TEST_CERTIFICATION_FILE);
    }

    public static CertificationRequest updateMockCertificationRequest() {
        return CertificationRequest.builder()
            .status(TEST_UPDATED_CERTIFICATION_STATUS)
            .description(TEST_UPDATED_CERTIFICATION_DESCRIPTION)
            .riderId(TEST_UPDATED_RIDER_ID)
            .missionId(TEST_UPDATED_MISSION_ID)
            .build();
    }

    public static MockMultipartFile updateMockCertificationMultipartFile() {
        return new MockMultipartFile(TEST_CERTIFICATION_MULTIPART_NAME, TEST_UPDATED_CERTIFICATION_FILE_NAME,
            MediaType.IMAGE_JPEG_VALUE, TEST_UPDATED_CERTIFICATION_FILE);
    }

    public static CertificationResponse createMockCertificationResponse() {
        return CertificationResponse.builder()
            .id(TEST_CERTIFICATION_ID)
            .image(TEST_CERTIFICATION_FILE_URL)
            .missionId(TEST_MISSION_ID)
            .riderId(TEST_RIDER_ID)
            .description(TEST_CERTIFICATION_DESCRIPTION)
            .status(TEST_CERTIFICATION_STATUS)
            .createdAt(TEST_CREATED_AT)
            .build();
    }

    public static CertificationResponses createMockCertificationResponses() {
        final List<CertificationResponse> mockResponses = Arrays.asList(
            createMockCertificationResponse(),
            createMockCertificationResponse(),
            createMockCertificationResponse(),
            createMockCertificationResponse()
        );
        final PageRequest pageRequest = PageRequest.of(0, 2, Sort.Direction.DESC, "id");

        return CertificationResponses.builder()
            .certifications(new PageImpl<>(mockResponses, pageRequest, mockResponses.size()))
            .build();
    }

    /*
    Service 테스트에서는 Repository를 Mocking하기 때문에 컨텐츠는 하나지만 총 4개인 것 처럼 만들었습니다.
    (Mocking하면 첫 페이지에 1개만 보여달라해도 4개의 컨텐츠를 모두 보여주기 떄문에)
     */
    public static Page<Certification> createMockPagedCertifications(final PageRequest request) {
        final List<Certification> mockCertifications = Arrays.asList(
            createCertificationWithId()
        );

        return new PageImpl<>(mockCertifications, request, 4);
    }
}
