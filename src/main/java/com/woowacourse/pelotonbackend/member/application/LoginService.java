package com.woowacourse.pelotonbackend.member.application;

public interface LoginService {
    String getCodeUrl();

    String createTokenUrl(String code);
}
