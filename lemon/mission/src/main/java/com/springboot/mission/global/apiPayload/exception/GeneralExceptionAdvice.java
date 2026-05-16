package com.springboot.mission.global.apiPayload.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GeneralExceptionAdvice {

    /**
     * DTO의 @Valid 유효성 검사 실패 시 발생하는 예외를 처리합니다.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationExceptions(
            MethodArgumentNotValidException ex) {

        Map<String, String> errors = new HashMap<>();

        // 1. 에러가 발생한 필드와 어노테이션에 지정한 message를 맵에 바인딩
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        // 2. 프로젝트 공통 규격 혹은 커스텀 에러 바디 구성
        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("isSuccess", false);
        responseBody.put("code", "COMMON_400");
        responseBody.put("message", "요청 데이터의 유효성 검사에 실패했습니다.");
        responseBody.put("result", errors); // 세부 필드 에러 내용 주입

        // 400 Bad Request 에러로 응답
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseBody);
    }
}
