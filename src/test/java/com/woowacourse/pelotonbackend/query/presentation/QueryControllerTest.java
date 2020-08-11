package com.woowacourse.pelotonbackend.query.presentation;

import static com.woowacourse.pelotonbackend.race.domain.RaceFixture.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpHeaders;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.woowacourse.pelotonbackend.docs.QueryDocumentation;
import com.woowacourse.pelotonbackend.member.domain.LoginFixture;
import com.woowacourse.pelotonbackend.member.domain.MemberFixture;
import com.woowacourse.pelotonbackend.member.presentation.LoginMemberArgumentResolver;
import com.woowacourse.pelotonbackend.member.presentation.dto.MemberResponse;
import com.woowacourse.pelotonbackend.race.domain.Race;
import com.woowacourse.pelotonbackend.race.domain.RaceRepository;
import com.woowacourse.pelotonbackend.rider.domain.Rider;
import com.woowacourse.pelotonbackend.rider.domain.RiderFixture;
import com.woowacourse.pelotonbackend.rider.domain.RiderRepository;
import com.woowacourse.pelotonbackend.support.BearerAuthInterceptor;

@WebMvcTest(QueryController.class)
@ExtendWith(RestDocumentationExtension.class)
class QueryControllerTest {
    private MockMvc mockMvc;

    @MockBean
    private RiderRepository riderRepository;

    @MockBean
    private RaceRepository raceRepository;

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
        final List<Long> ridersRaceId = riders.stream()
            .mapToLong(rider -> rider.getRaceId().getId())
            .boxed()
            .collect(Collectors.toList());
        final List<Race> races = riders.stream()
            .map(rider -> createWithId(rider.getRaceId().getId()))
            .collect(Collectors.toList());
        given(bearerAuthInterceptor.preHandle(any(HttpServletRequest.class), any(HttpServletResponse.class),
            any(HandlerMethod.class))).willReturn(true);
        given(argumentResolver.resolveArgument(any(MethodParameter.class), any(ModelAndViewContainer.class),
            any(NativeWebRequest.class), any(WebDataBinderFactory.class))).willReturn(loginMember);
        given(argumentResolver.supportsParameter(any())).willReturn(true);
        given(riderRepository.findRidersByMemberId(loginMember.getId())).willReturn(riders);
        given(raceRepository.findAllById(ridersRaceId)).willReturn(races);

        mockMvc.perform(get("/api/queries/races")
            .header(HttpHeaders.AUTHORIZATION, LoginFixture.getTokenHeader())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andDo(QueryDocumentation.getRaces())
            .andReturn();
    }
}
