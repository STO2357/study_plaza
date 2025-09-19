package sto.study_plaza.integration.controller;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;
import sto.study_plaza.dto.auth.LogInRequest;
import sto.study_plaza.dto.auth.SignUpRequest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = sto.study_plaza.StudyPlazaApplication.class)
@Transactional
@AutoConfigureMockMvc
class AuthControllerIntegrationTest {


    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    ApplicationContext context;

    @Test
    void signup_success() throws Exception {

        SignUpRequest request = new SignUpRequest("user1", "홍길동", "pass123");

        MvcResult result = mockMvc.perform(post("/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"userId\":\"user1\",\"name\":\"홍길동\",\"password\":\"pass123\"}"))
                .andExpect(status().isOk())
                .andReturn();

        System.out.println("응답 Body = " + result.getResponse().getContentAsString());

        mockMvc.perform(post("/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("success"))
                .andExpect(jsonPath("$.data").value("user1"));
    }

    @Test
    void signup_fail_validationError() throws Exception {

        SignUpRequest request = new SignUpRequest("", "홍길동", "pass123");

        mockMvc.perform(post("/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void login_success() throws Exception {

        SignUpRequest signUpRequest = new SignUpRequest("user2", "이순신", "pass123");
        mockMvc.perform(post("/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(signUpRequest)))
                .andExpect(status().isOk());

        // when & then
        LogInRequest loginRequest = new LogInRequest("user2", "pass123");

        mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("success"))
                .andExpect(jsonPath("$.data").isNotEmpty());
    }

    @Test
    void login_fail_invalidCredentials() throws Exception {
        LogInRequest loginRequest = new LogInRequest("notExists", "wrongPass");

        mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isUnauthorized());
    }
}
