package sto.study_plaza.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import sto.study_plaza.controller.response.ApiResponse;
import sto.study_plaza.dto.auth.LogInRequest;
import sto.study_plaza.dto.auth.SignUpRequest;
import sto.study_plaza.service.member.MemberCommandService;

import java.io.IOException;
import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AuthController {

    private final MemberCommandService memberCommandService;

    @PostMapping(value = "/signup")
    public ApiResponse<String> createMember(
            @RequestBody @Valid SignUpRequest signUpRequest) throws IOException {
        log.info("[AuthController] /signup 진입 - 요청 데이터: {}", signUpRequest);

        memberCommandService.signUp(signUpRequest);

        log.info("[AuthController] 회원가입 성공 - userId: {}", signUpRequest.getUserId());
        return ApiResponse.success(signUpRequest.getUserId());
    }

    @PostMapping(value = "/login")
    public ApiResponse<UUID> login(@RequestBody @Validated LogInRequest logInRequest) {
        UUID userId = memberCommandService.login(logInRequest);
        return ApiResponse.success(userId);
    }

}
