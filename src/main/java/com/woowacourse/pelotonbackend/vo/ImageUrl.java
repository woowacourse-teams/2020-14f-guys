package com.woowacourse.pelotonbackend.vo;

import javax.validation.constraints.NotBlank;

import lombok.Value;

@Value
public class ImageUrl {
    @NotBlank
    private final String baseImageUrl;
}
