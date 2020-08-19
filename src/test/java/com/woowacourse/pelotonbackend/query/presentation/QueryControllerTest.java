package com.woowacourse.pelotonbackend.query.presentation;

import static com.woowacourse.pelotonbackend.race.domain.RaceFixture.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.DayOfWeek;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpHeaders;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.MethodParameter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.woowacourse.pelotonbackend.certification.domain.Certification;
import com.woowacourse.pelotonbackend.certification.domain.CertificationFixture;
import com.woowacourse.pelotonbackend.common.ErrorCode;
import com.woowacourse.pelotonbackend.common.exception.RaceNotFoundException;
import com.woowacourse.pelotonbackend.common.exception.TokenInvalidException;
import com.woowacourse.pelotonbackend.docs.QueryDocumentation;
import com.woowacourse.pelotonbackend.member.domain.LoginFixture;
import com.woowacourse.pelotonbackend.member.domain.MemberFixture;
import com.woowacourse.pelotonbackend.member.presentation.LoginMemberArgumentResolver;
import com.woowacourse.pelotonbackend.member.presentation.dto.MemberResponse;
import com.woowacourse.pelotonbackend.mission.domain.MissionFixture;
import com.woowacourse.pelotonbackend.query.application.QueryService;
import com.woowacourse.pelotonbackend.query.presentation.dto.RaceCertificationsResponse;
import com.woowacourse.pelotonbackend.query.presentation.dto.RaceDetailResponse;
import com.woowacourse.pelotonbackend.query.presentation.dto.UpcomingMissionResponse;
import com.woowacourse.pelotonbackend.query.presentation.dto.UpcomingMissionResponses;
import com.woowacourse.pelotonbackend.race.domain.Race;
import com.woowacourse.pelotonbackend.race.domain.RaceFixture;
import com.woowacourse.pelotonbackend.race.presentation.dto.RaceResponses;
import com.woowacourse.pelotonbackend.rider.domain.Rider;
import com.woowacourse.pelotonbackend.rider.domain.RiderFixture;
import com.woowacourse.pelotonbackend.support.BearerAuthInterceptor;

@WebMvcTest(QueryController.class)
@ExtendWith(RestDocumentationExtension.class)
class QueryControllerTest {
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private QueryService queryService;

    @MockBean
    private BearerAuthInterceptor bearerAuthInterceptor;

    @MockBean
    private LoginMemberArgumentResolver argumentResolver;

    @BeforeEach
    void setUp(WebApplicationContext webApplicationContext, RestDocumentationContextProvider restDocumentation) {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
            .apply(documentationConfiguration(restDocumentation))
            .addFilters(new CharacterEncodingFilter("UTF-8", true))
            .alwaysDo(print())
            .build();
    }

    @DisplayName("로그인 되어있는 Member의 id로 모든 race를 조회할 수 있어야 한다.")
    @Test
    void retrieveRacesByTest() throws Exception {
        final MemberResponse loginMember = MemberResponse.from(MemberFixture.createWithId(11L));
        final List<Rider> riders = RiderFixture.createRidersBy(loginMember.getId());
        final List<Race> races = riders.stream()
            .map(rider -> createWithId(rider.getRaceId().getId()))
            .collect(Collectors.toList());
        given(bearerAuthInterceptor.preHandle(any(HttpServletRequest.class), any(HttpServletResponse.class),
            any(HandlerMethod.class))).willReturn(true);
        given(argumentResolver.resolveArgument(any(MethodParameter.class), any(ModelAndViewContainer.class),
            any(NativeWebRequest.class), any(WebDataBinderFactory.class))).willReturn(loginMember);
        given(argumentResolver.supportsParameter(any())).willReturn(true);
        given(queryService.retrieveRacesBy(loginMember)).willReturn(RaceResponses.of(races));

        final MvcResult result = mockMvc.perform(get("/api/queries/races")
            .header(HttpHeaders.AUTHORIZATION, LoginFixture.getTokenHeader())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andDo(QueryDocumentation.getRaces())
            .andReturn();

        final byte[] contentBytes = result.getResponse().getContentAsByteArray();
        final RaceResponses responseBody = objectMapper.readValue(contentBytes, RaceResponses.class);

        assertAll(
            () -> assertThat(responseBody.getRaceResponses().get(0))
                .isEqualToComparingFieldByField(RaceFixture.retrieveResponse()),
            () -> assertThat(responseBody.getRaceResponses()).hasSize(4)
        );
    }

    @DisplayName("유효하지 않은 token로 race를 조회할 때 예외처리한다.")
    @Test
    void retrieveRacesByTest2() throws Exception {
        given(bearerAuthInterceptor.preHandle(any(HttpServletRequest.class), any(HttpServletResponse.class),
            any(HandlerMethod.class))).willThrow(
            new TokenInvalidException(ErrorCode.INVALID_TOKEN, "토큰이 이상합니다~~~~~~~~~~"));

        mockMvc.perform(get("/api/queries/races")
            .header(HttpHeaders.AUTHORIZATION, "Invalid token")
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isUnauthorized())
            .andDo(QueryDocumentation.getRacesFail());
    }

    @DisplayName("레이스의 아이디로 인증을 조회한다.")
    @Test
    void findCertificationsByRaceId() throws Exception {
        final Page<Certification> pagedCertifications =
            CertificationFixture.createMockPagedCertifications(PageRequest.of(0, 1));
        final RaceCertificationsResponse response = RaceCertificationsResponse.of(pagedCertifications);

        when(queryService.findCertificationsByRaceId(anyLong(), any(Pageable.class))).thenReturn(response);
        when(bearerAuthInterceptor.preHandle(any(HttpServletRequest.class), any(HttpServletResponse.class),
            any(HandlerMethod.class))).thenReturn(true);

        mockMvc.perform(get("/api/queries/races/{raceId}/certifications", RaceFixture.TEST_RACE_ID)
            .header(HttpHeaders.AUTHORIZATION, LoginFixture.getTokenHeader())
            .accept(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isOk())
            .andDo(QueryDocumentation.findCertificationsByRaceId());
    }

    @DisplayName("존재하지 않는 race의 아이디로 인증을 조회한다.")
    @Test
    void findCertificationByNotExistRaceId() throws Exception {
        when(queryService.findCertificationsByRaceId(anyLong(), any(Pageable.class)))
            .thenThrow(new RaceNotFoundException(RaceFixture.TEST_RACE_ID));
        when(bearerAuthInterceptor.preHandle(any(HttpServletRequest.class), any(HttpServletResponse.class),
            any(HandlerMethod.class))).thenReturn(true);

        mockMvc.perform(get("/api/queries/races/{raceId}/certifications", RaceFixture.TEST_RACE_ID)
            .header(HttpHeaders.AUTHORIZATION, LoginFixture.getTokenHeader())
            .accept(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isNotFound())
            .andDo(QueryDocumentation.findCertificationsByNotExistRaceId());
    }

    @DisplayName("멤버가 인증해야할 미션을 조회한다.")
    @Test
    void findUpcomingMissions() throws Exception {
        final UpcomingMissionResponse noCertificationMission = MissionFixture.upcomingMissionResponseWithoutCertification();
        final UpcomingMissionResponse hasCertificationMission = MissionFixture.upcomingMissionResponseWithCertification();
        final UpcomingMissionResponses expectedResponses = UpcomingMissionResponses.of(
            Arrays.asList(noCertificationMission, hasCertificationMission));
        final MemberResponse member = MemberFixture.memberResponse();
        when(argumentResolver.resolveArgument(any(MethodParameter.class), any(ModelAndViewContainer.class),
            any(NativeWebRequest.class), any(WebDataBinderFactory.class))).thenReturn(member);
        when(argumentResolver.supportsParameter(any())).thenReturn(true);
        when(queryService.retrieveUpcomingMissionsBy(member)).thenReturn(expectedResponses);
        when(bearerAuthInterceptor.preHandle(any(HttpServletRequest.class), any(HttpServletResponse.class),
            any(HandlerMethod.class))).thenReturn(true);

        final byte[] content = mockMvc.perform(get("/api/queries/missions/upcoming")
            .header(HttpHeaders.AUTHORIZATION, LoginFixture.getTokenHeader())
            .accept(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isOk())
            .andDo(QueryDocumentation.getUpcomingMissions())
            .andReturn()
            .getResponse()
            .getContentAsByteArray();

        final UpcomingMissionResponses responses = objectMapper.readValue(content, UpcomingMissionResponses.class);
        assertThat(responses).usingRecursiveComparison().isEqualTo(expectedResponses);
    }

    @DisplayName("레이스의 아이디로 레이스 상세정보를 조회한다.")
    @Test
    void findRaceDetail() throws Exception {
        final Race race = createWithId(TEST_RACE_ID);
        final List<DayOfWeek> days =  Arrays.asList(DayOfWeek.MONDAY, DayOfWeek.WEDNESDAY, DayOfWeek.FRIDAY);
        final RaceDetailResponse response = RaceDetailResponse.of(race, MissionFixture.MISSION_DURATION, days);

        when(queryService.findRaceDetail(anyLong())).thenReturn(response);
        when(bearerAuthInterceptor.preHandle(any(HttpServletRequest.class), any(HttpServletResponse.class),
            any(HandlerMethod.class))).thenReturn(true);

        final MvcResult mvcResult = mockMvc.perform(get("/api/queries/races/{raceId}/detail", TEST_RACE_ID)
            .header(HttpHeaders.AUTHORIZATION, LoginFixture.getTokenHeader())
            .accept(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isOk())
            .andReturn();

        final byte[] content = mvcResult.getResponse().getContentAsByteArray();
        final RaceDetailResponse actualResponse = objectMapper.readValue(content, RaceDetailResponse.class);

        assertAll(
            () -> assertThat(actualResponse).isEqualToComparingOnlyGivenFields(race, "id", "title", "description", "thumbnail", "certificationExample", "category", "entranceFee", "raceDuration"),
            () -> assertThat(actualResponse.getMissionDuration()).isEqualTo(MissionFixture.MISSION_DURATION),
            () -> assertThat(actualResponse.getDays()).isEqualTo(days)
        );
    }
}
