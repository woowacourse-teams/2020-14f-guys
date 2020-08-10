package com.woowacourse.pelotonbackend.certification.presentation;

import java.beans.ConstructorProperties;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.woowacourse.pelotonbackend.certification.domain.Certification;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE, onConstructor_ = @ConstructorProperties("certifications"))
@Builder
@Getter
public class CertificationResponses {
    private final Page<CertificationResponse> certifications;

    public static CertificationResponses of(final Page<Certification> certificationPage) {
        final List<CertificationResponse> contents = certificationPage.stream()
            .map(CertificationResponse::of)
            .collect(Collectors.toList());
        final long count = certificationPage.getTotalElements();
        final Pageable pageable = certificationPage.getPageable();

        return new CertificationResponses(new PageImpl<>(contents, pageable, count));
    }
}
