package com.woowacourse.pelotonbackend.vo;

import java.beans.ConstructorProperties;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor(onConstructor_ = @ConstructorProperties("baseImageUrl"))
public class ImageUrl {
    @NotBlank
    private final String baseImageUrl;
}
