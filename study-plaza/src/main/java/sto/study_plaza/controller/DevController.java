package sto.study_plaza.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import sto.study_plaza.controller.response.ApiResponse;
import sto.study_plaza.service.HealthCheckService;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/dev")
@RequiredArgsConstructor
public class DevController {

    private final HealthCheckService healthCheckService;

    @GetMapping("/health-check")
    public Map<String, Object> healthCheck(@RequestParam String input) {
        return healthCheckService.runHealthCheck(input);
    }

    @GetMapping("/test-error")
    public String triggerError() {
        throw new RuntimeException("테스트용 예외 발생!");
    }

    @GetMapping("/test-success")
    public ApiResponse<String> triggerResponse() {
        return ApiResponse.success("테스트 성공 데이터");
    }

    @GetMapping("/test-timezone")
    public ApiResponse<Map<String, Object>> testTimezone() {
        ZonedDateTime nowSeoul = ZonedDateTime.now(ZoneId.of("Asia/Seoul"));
        ZonedDateTime nowUTC = ZonedDateTime.now(ZoneId.of("UTC"));

        long hoursDiff = nowSeoul.getOffset().getTotalSeconds() / 3600;
        boolean isUTCPlus9 = (hoursDiff == 9);

        Map<String, Object> result = new HashMap<>();
        result.put("nowSeoul", nowSeoul);
        result.put("nowUTC", nowUTC);
        result.put("hoursDiff", hoursDiff);
        result.put("isUTCPlus9", isUTCPlus9);

        return ApiResponse.success(result);
    }

    @GetMapping("/test-encoding")
    public ApiResponse<String> testEncoding() {
        String testString = "한글: 안녕하세요 | Japanese: こんにちは | Chinese: 你好 | Emoji: 😀🔥💯 | Special: ©®™✓✗★";
        return ApiResponse.success(testString);
    }

}