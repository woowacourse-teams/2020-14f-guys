package com.woowacourse.pelotonbackend.vo;

import java.beans.ConstructorProperties;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.woowacourse.pelotonbackend.support.jsonparser.CashDeserializer;
import com.woowacourse.pelotonbackend.support.jsonparser.CashSerializer;
import com.woowacourse.pelotonbackend.support.jsonparser.ImageUrlDeserializer;
import com.woowacourse.pelotonbackend.support.jsonparser.ImageUrlSerializer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Value;

@AllArgsConstructor(onConstructor_ = @ConstructorProperties("baseImageUrl"))
@Getter
@Value
@JsonSerialize(using = ImageUrlSerializer.class)
@JsonDeserialize(using= ImageUrlDeserializer.class)
public class ImageUrl {
    @NotBlank
    private final String baseImageUrl;
}
