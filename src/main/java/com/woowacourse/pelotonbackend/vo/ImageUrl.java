package com.woowacourse.pelotonbackend.vo;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ImageUrl {
    @NotBlank
    private final String baseImageUrl;
}
