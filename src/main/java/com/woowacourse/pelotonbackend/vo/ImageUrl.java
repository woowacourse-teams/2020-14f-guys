package com.woowacourse.pelotonbackend.vo;

import java.beans.ConstructorProperties;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Value;

@AllArgsConstructor(onConstructor_ = @ConstructorProperties("baseImageUrl"))
@Value
public class ImageUrl {
    @NotBlank
    private final String baseImageUrl;
}
