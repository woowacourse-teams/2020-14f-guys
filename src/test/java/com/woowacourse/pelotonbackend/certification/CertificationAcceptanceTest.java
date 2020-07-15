package com.woowacourse.pelotonbackend.certification;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Base64;
import java.util.HashMap;
import java.util.stream.Stream;

import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class CertificationAcceptanceTest {
    private static final String status = "SUCCESS";
    private static final String description = "좋은 인증이다..";
    private static final MockMultipartFile encodedImage = new MockMultipartFile("file", "dd.png", "text/plain", "안녕하세요".getBytes());

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @TestFactory
    public Stream<DynamicTest> certificationTest() {
        return Stream.of(
            DynamicTest.dynamicTest("Test certification create", () -> {
                final HashMap<String, Object> body = new HashMap<>();
                body.put("status", status);
                body.put("description", description);

                mockMvc.perform(multipart("/api/certification/rider/{riderId}/mission/{missionId}",1L, 1L)
                    .file(encodedImage)
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .content(objectMapper.writeValueAsString(body)))
                    .andDo(print())
                    .andExpect(status().isCreated())
                    .andExpect(header().stringValues("location", "/certification/1"));
            })
        );
    }
}
