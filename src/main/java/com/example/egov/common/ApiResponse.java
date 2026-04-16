package com.example.egov.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

/**
 * 공통 API 응답 래퍼
 * 전자정부프레임워크 5.0 표준 응답 포맷
 */
@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {

    private final boolean success;
    private final String resultCode;
    private final String resultMsg;
    private final T result;

    public static <T> ApiResponse<T> success(T data) {
        return ApiResponse.<T>builder()
            .success(true).resultCode("00")
            .resultMsg("정상 처리되었습니다.").result(data).build();
    }

    public static <T> ApiResponse<T> success(String message) {
        return ApiResponse.<T>builder()
            .success(true).resultCode("00").resultMsg(message).build();
    }

    public static <T> ApiResponse<T> fail(String code, String message) {
        return ApiResponse.<T>builder()
            .success(false).resultCode(code).resultMsg(message).build();
    }
}
