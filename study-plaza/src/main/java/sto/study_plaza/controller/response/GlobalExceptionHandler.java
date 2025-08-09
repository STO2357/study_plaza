package sto.study_plaza.controller.response;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ApiResponse<Void> handleException(Exception e) {
        log.error("서버 에러 발생", e);

        return ApiResponse.error(
                "INTERNAL_SERVER_ERROR",
                "서버에서 알 수 없는 오류가 발생했습니다."
        );
    }
}