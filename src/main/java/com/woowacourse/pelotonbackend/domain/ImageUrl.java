package com.woowacourse.pelotonbackend.domain;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ImageUrl {
    @NotBlank
    private final String photoUrl1x;

    @NotBlank
    private final String photoUrl2x;

    @NotBlank
    private final String photoUrl3x;
}
