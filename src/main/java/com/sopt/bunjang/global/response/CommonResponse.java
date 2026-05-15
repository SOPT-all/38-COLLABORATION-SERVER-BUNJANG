package com.sopt.bunjang.global.response;

import com.sopt.bunjang.global.exception.ErrorCode;

// 공통 응답 핸들러
public record CommonResponse<T>(
        boolean isSuccess,
        String code,
        String message,
        T data
) {

    // 성공(data가 있는 경우)
    public static <T> CommonResponse<T> success(SuccessCode successCode, T data) {
        return new CommonResponse<>(
                true,
                successCode.getCode(),
                successCode.getMessage(),
                data
        );
    }

    // 성공(data가 없는 경우)
    public static CommonResponse<Void> success(SuccessCode successCode) {
        return new CommonResponse<>(
                true,
                successCode.getCode(),
                successCode.getMessage(),
                null
        );
    }

    // 실패(data가 있는 경우)
    public static <T> CommonResponse<T> fail(ErrorCode errorCode, T data) {
        return new CommonResponse<>(
                false,
                errorCode.getCode(),
                errorCode.getMessage(),
                data
        );
    }

    // 실패(data가 없는 경우)
    public static CommonResponse<Void> fail(ErrorCode errorCode) {
        return new CommonResponse<>(
                false,
                errorCode.getCode(),
                errorCode.getMessage(),
                null
        );
    }
}