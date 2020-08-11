package com.woowacourse.pelotonbackend.docs;

import static com.woowacourse.pelotonbackend.docs.ApiDocumentationUtils.*;
import static com.woowacourse.pelotonbackend.docs.DocumentFormatGenerator.*;
import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.restdocs.payload.JsonFieldType.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;

import org.apache.http.HttpHeaders;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;

public class MemberDocumentation {
    public static RestDocumentationResultHandler createMember() {
        return document("member/create-success",
            getDocumentRequest(),
            getDocumentResponse(),
            requestFields(
              fieldWithPath("kakao_id").type(NUMBER).description("kakao Id"),
              fieldWithPath("profile").type(STRING).description("profile Image Url"),
              fieldWithPath("name").type(STRING).description("member name"),
              fieldWithPath("email").type(STRING).attributes(getEmailFormat()).description("member email"),
              fieldWithPath("cash").type(STRING).description("member cash"),
              fieldWithPath("role").type(STRING).attributes(getMemberRoleFormat()).description("member role")
            ),
            responseHeaders(
                headerWithName(HttpHeaders.LOCATION).description("Resource의 Location 헤더")
            )
        );
    }

    public static RestDocumentationResultHandler getMember() {
        return document("member/get-success",
            getDocumentRequest(),
            getDocumentResponse(),
            requestHeaders(
                headerWithName(HttpHeaders.AUTHORIZATION).description("사용자 인증 Access Token 헤더"),
                headerWithName(HttpHeaders.ACCEPT).description("Accept 헤더")
            ),
            responseHeaders(
                headerWithName(HttpHeaders.CONTENT_TYPE).description("Content-Type 헤더")
            ),
            responseFields(
                fieldWithPath("id").type(NUMBER).description("Member id"),
                fieldWithPath("kakao_id").type(NUMBER).description("Member kakao id"),
                subsectionWithPath("profile").type(STRING).description("Member profile image url"),
                fieldWithPath("name").type(STRING).description("Member name"),
                fieldWithPath("email").type(STRING).attributes(getEmailFormat()).description("Member email"),
                subsectionWithPath("cash").type(STRING).description("Member cash"),
                fieldWithPath("role").type(STRING).attributes(getMemberRoleFormat()).description("Member role")
            )
        );
    }

    public static RestDocumentationResultHandler getAllMembers() {
        return document("member/get-all-success",
            getDocumentRequest(),
            getDocumentResponse(),
            requestHeaders(
                headerWithName(HttpHeaders.AUTHORIZATION).description("사용자 인증 Access Token 헤더"),
                headerWithName(HttpHeaders.ACCEPT).description("Accept 헤더")
            ),
            responseHeaders(
                headerWithName(HttpHeaders.CONTENT_TYPE).description("Content-Type 헤더")
            ),
            responseFields(
                subsectionWithPath("responses").type(ARRAY).description("Members 목록")
            )
        );
    }

    public static RestDocumentationResultHandler updateName() {
        return document("member/update-name",
            getDocumentRequest(),
            getDocumentResponse(),
            requestHeaders(
                headerWithName(HttpHeaders.AUTHORIZATION).description("사용자 인증 Access Token 헤더"),
                headerWithName(HttpHeaders.CONTENT_TYPE).description("Content-Type 헤더")
            ),
            requestFields(
                fieldWithPath("name").type(STRING).description("변경될 Member 이름")
            ),
            responseHeaders(
                headerWithName(HttpHeaders.LOCATION).description("Resource의 Location 헤더")
            )
        );
    }

    public static RestDocumentationResultHandler updateCash() {
        return document("member/update-cash",
            getDocumentRequest(),
            getDocumentResponse(),
            requestHeaders(
                headerWithName(HttpHeaders.AUTHORIZATION).description("사용자 인증 Access Token 헤더"),
                headerWithName(HttpHeaders.CONTENT_TYPE).description("Content-Type 헤더")
            ),
            requestFields(
                fieldWithPath("cash").type(STRING).description("변경될 Member 보유 Cash")
            ),
            responseHeaders(
                headerWithName(HttpHeaders.LOCATION).description("Resource의 Location 헤더")
            )
        );
    }

    public static RestDocumentationResultHandler updateProfileImage() {
        return document("member/update-profile-image",
            getDocumentRequest(),
            getDocumentResponse(),
            requestHeaders(
                headerWithName(HttpHeaders.AUTHORIZATION).description("사용자 인증 Access Token 헤더"),
                headerWithName(HttpHeaders.CONTENT_TYPE).description("Content-Type 헤더")
            ),
            requestParts(
                partWithName("profile_image").description("변경 될 Profile Image")
            ),
            responseHeaders(
                headerWithName(HttpHeaders.CONTENT_TYPE).description("Response Content Type 헤더")
            ),
            responseFields(
                fieldWithPath("image_url").type(STRING).description("수정된 이미지 url")
            )
        );
    }

    public static RestDocumentationResultHandler deleteMember() {
        return document("member/delete-success",
            getDocumentRequest(),
            getDocumentResponse(),
            requestHeaders(
                headerWithName(HttpHeaders.AUTHORIZATION).description("사용자 인증 Access Token 헤더")
            )
        );
    }

    public static RestDocumentationResultHandler createBadMember() {
        return document("member/create-fail",
            getDocumentRequest(),
            getDocumentResponse(),
            requestHeaders(
                headerWithName(HttpHeaders.AUTHORIZATION).description("사용자 Access Token"),
                headerWithName(HttpHeaders.CONTENT_TYPE).description("Content-Type Header")
            ),
            getErrorResponseFieldsWithFieldErrors()
        );
    }

    public static RestDocumentationResultHandler getNotExistMember() {
        return document("member/get-fail",
            getDocumentRequest(),
            getDocumentResponse(),
            requestHeaders(
                headerWithName(HttpHeaders.AUTHORIZATION).description("사용자 인증 Access Token 헤더"),
                headerWithName(HttpHeaders.ACCEPT).description("Accept 헤더")
            ),
            getErrorResponseFields()
        );
    }
}
