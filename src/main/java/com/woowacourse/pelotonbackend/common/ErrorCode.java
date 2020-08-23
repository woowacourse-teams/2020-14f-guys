package com.woowacourse.pelotonbackend.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorCode {
    BUSINESS(500, "서버에서 문제가 생겼어요.🤣 조금만 기다려주세요."),
    UNEXPECTED(500, "서버에서 알 수 없는 예외가 발생했어요.😂 조금만 기다려주세요."),

    INVALID_VALIDATE(400, "입력이 이상해요.😅"),
    UN_AUTHENTICATE(403, "권한이 없습니다.😂"),

    MEMBER_NOT_FOUND(404, "해당 회원을 찾을 수 없어요.😂"),
    MEMBER_DUPLICATE(400, "이미 가입한 회원이에요.😀"),
    MEMBER_ID_INVALID(400, "잘못된 회원 아이디에요.😂"),

    CALCULATION_DUPLICATE(400, "이미 정산 받은 회원이에요!😂"),
    CALCULATION_NOT_FOUND(404, "아직 정산이 완료되지 않았습니다."),

    REPORT_NOT_FOUND(404, "해당 신고를 찾을 수 없어요.😂"),
    REPORT_DUPLICATE(400, "이미 신고 되었어요!😀"),
    REPORT_ID_INVALID(400, "잘못된 신고 아이디에요.😂"),

    RACE_NOT_FOUND(404, "해당 레이스를 찾을 수 없어요.😂"),
    RACE_DUPLICATE(400, "이미 존재하는 레이스에요.😊"),
    RACE_ID_INVALID(400, "잘못된 레이스 아이디에요.😂"),
    RACE_NOT_FINISHED(400, "레이스가 아직 진행중이에요!😊"),

    MISSION_NOT_FOUND(404, "미션을 찾을 수 없어요.😂"),
    MISSION_DUPLICATE(400, "이미 존재하는 미션에요.😊"),
    MISSION_ID_INVALID(400, "잘못된 미션 아이디에요.😂"),
    MISSION_NOT_CREATED(400, "설정한 기간에 설정한 요일이 포함되지 않아요😂"),

    RIDER_NOT_FOUND(404,"해당 라이더를 찾을 수 없어요.😂"),
    RIDER_DUPLICATE(400, "이미 참여한 레이스에요.😊"),
    RIDER_ID_INVALID(400, "해당 라이더가 없어요.😂"),

    UN_AUTHORIZED(401, "인증이 잘못되었어요.😂"),
    TOKEN_EXPIRED(401, "토큰이 만료되었어요. 다시 로그인 해주세요.😂"),
    INVALID_TOKEN(401, "잘못된 토큰이에요.😂"),

    FILE_UPLOAD(400, "파일 형식이 잘못되었어요.!📁"),

    CERTIFICATION_NOT_FOUND(404, "인증 정보를 찾을 수 없어요.😂"),
    CERTIFICATION_DUPLICATE(400, "이미 인증하셨어요.😀"),

    MONEY_NOT_ENOUGH(400, "캐쉬가 부족해요😂 충전해주세요.");

    private final int status;
    private final String code;
}
