package sto.study_plaza.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import sto.study_plaza.service.HealthCheckService;

import java.util.Map;

@RestController
@RequestMapping("/api/dev")
@RequiredArgsConstructor
public class DevController {

    private final HealthCheckService healthCheckService;

    @GetMapping("/health-check")
    public Map<String, Object> healthCheck(@RequestParam String input) {
        return healthCheckService.runHealthCheck(input);
    }
}