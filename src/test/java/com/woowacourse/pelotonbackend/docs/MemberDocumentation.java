package com.woowacourse.pelotonbackend.docs;

import static com.woowacourse.pelotonbackend.docs.ApiDocumentationUtils.*;
import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.restdocs.payload.JsonFieldType.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;

import org.apache.http.HttpHeaders;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.ResultHandler;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.woowacourse.pelotonbackend.member.domain.Role;
import com.woowacourse.pelotonbackend.support.jsonparser.CashDeserializer;
import com.woowacourse.pelotonbackend.support.jsonparser.CashSerializer;
import com.woowacourse.pelotonbackend.vo.Cash;
import com.woowacourse.pelotonbackend.vo.ImageUrl;

public class MemberDocumentation {
    public static RestDocumentationResultHandler createMember() {
        return document("member/create-success",
            getDocumentRequest(),
            getDocumentResponse(),
            requestFields(
              fieldWithPath("kakaoId").type(NUMBER).description("kakao Id"),
              subsectionWithPath("profile").description("profile Image Url"),
              fieldWithPath("name").type(STRING).description("member name"),
              fieldWithPath("email").type(STRING).description("member email"),
              subsectionWithPath("cash").type(OBJECT).description("member cash"),
              fieldWithPath("role").type(STRING).description("member role")
            ),
            responseHeaders(
                headerWithName(HttpHeaders.LOCATION).description("Location 헤더")
            )
        );
    }

    public static RestDocumentationResultHandler getMember() {
        return document("member/get-success",
            getDocumentRequest(),
            getDocumentResponse(),
            requestHeaders(
                headerWithName(HttpHeaders.AUTHORIZATION).description("Authorization 헤더"),
                headerWithName(HttpHeaders.ACCEPT).description("Accept 헤더")
            ),
            responseHeaders(
                headerWithName(HttpHeaders.CONTENT_TYPE).description("Content-Type 헤더")
            ),
            responseFields(
                fieldWithPath("id").type(NUMBER).description("Member id"),
                fieldWithPath("kakaoId").type(NUMBER).description("Member kakao id"),
                subsectionWithPath("profile").type(OBJECT).description("Member profile image url"),
                fieldWithPath("name").type(STRING).description("Member name"),
                fieldWithPath("email").type(STRING).description("Member email"),
                subsectionWithPath("cash").type(STRING).description("Member cash"),
                fieldWithPath("role").type(STRING).description("Member role")
            )
        );
    }

    public static RestDocumentationResultHandler getAllMembers() {
        return document("member/get-all-success",
            getDocumentRequest(),
            getDocumentResponse(),
            requestHeaders(
                headerWithName(HttpHeaders.AUTHORIZATION).description("Authorization 헤더"),
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
                headerWithName(HttpHeaders.AUTHORIZATION).description("Authorization 헤더"),
                headerWithName(HttpHeaders.CONTENT_TYPE).description("Content-Type 헤더")
            ),
            requestFields(
                fieldWithPath("name").type(STRING).description("변경될 Member 이름")
            ),
            responseHeaders(
                headerWithName(HttpHeaders.LOCATION).description("Location 헤더")
            )
        );
    }

    public static RestDocumentationResultHandler updateCash() {
        return document("member/update-cash",
            getDocumentRequest(),
            getDocumentResponse(),
            requestHeaders(
                headerWithName(HttpHeaders.AUTHORIZATION).description("Authorization 헤더"),
                headerWithName(HttpHeaders.CONTENT_TYPE).description("Content-Type 헤더")
            ),
            requestFields(
                fieldWithPath("cash").type(STRING).description("변경될 Member 보유 Cash")
            ),
            responseHeaders(
                headerWithName(HttpHeaders.LOCATION).description("Location 헤더")
            )
        );
    }

    public static RestDocumentationResultHandler updateProfileImage() {
        return document("member/update-profile-image",
            getDocumentRequest(),
            getDocumentResponse(),
            requestHeaders(
                headerWithName(HttpHeaders.AUTHORIZATION).description("Authorization 헤더"),
                headerWithName(HttpHeaders.CONTENT_TYPE).description("Content-Type 헤더")
            ),
            requestParts(
                partWithName("profile_image").description("변경 될 Profile Image")
            ),
            responseHeaders(
                headerWithName(HttpHeaders.CONTENT_TYPE).description("Response Content Type 헤더")
            ),
            responseFields(
                fieldWithPath("imageUrl").type(STRING).description("수정된 이미지 url")
            )
        );
    }

    public static RestDocumentationResultHandler deleteMember() {
        return document("member/delete",
            getDocumentRequest(),
            getDocumentResponse(),
            requestHeaders(
                headerWithName(HttpHeaders.AUTHORIZATION).description("Authorization 헤더")
            )
        );
    }
}
