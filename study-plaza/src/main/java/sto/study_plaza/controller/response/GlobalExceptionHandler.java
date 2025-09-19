package sto.study_plaza.controller.response;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.naming.AuthenticationException;
import java.nio.file.AccessDeniedException;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /* 400 - Validation Errors */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse<String> handleValidationException(MethodArgumentNotValidException e) {
        String errors = e.getBindingResult().getFieldErrors()
                .stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.joining(", "));
        log.warn("Validation failed: {}", errors);
        return ApiResponse.error("VALIDATION_ERROR", errors);
    }

    /* 401 - 인증 실패 (BadCredentials, TokenExpired 등) */
    @ExceptionHandler({BadCredentialsException.class})
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ApiResponse<Void> handleUnauthorizedException(RuntimeException e) {
        log.warn("Authentication failed: {}", e.getMessage());
        return ApiResponse.error("AUTH_ERROR", e.getMessage());
    }

    /* 403 - 권한 없음 */
    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ApiResponse<Void> handleForbiddenException(AccessDeniedException e) {
        log.warn("Access denied: {}", e.getMessage());
        return ApiResponse.error("FORBIDDEN", e.getMessage());
    }

    /* 404 - Resource Not Found */
    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiResponse<Void> handleNotFoundException(EntityNotFoundException e) {
        log.warn("Resource not found", e);
        return ApiResponse.error("NOT_FOUND", e.getMessage());
    }

    /* 409 - Conflict */
    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiResponse<Void> handleConflictException(DataIntegrityViolationException e) {
        log.warn("Data conflict", e);
        return ApiResponse.error("CONFLICT", "데이터 충돌 또는 중복 발생");
    }

    /* 500 - General / Unknown Errors */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiResponse<Void> handleGeneralException(Exception e) {
        log.error("Server error", e);
        return ApiResponse.error("INTERNAL_SERVER_ERROR", "서버에서 알 수 없는 오류가 발생했습니다.");
    }
}