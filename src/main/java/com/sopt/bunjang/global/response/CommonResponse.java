package com.sopt.bunjang.global.response;

// 공통 응답 핸들러
public record CommonResponse<T> (
    boolean isSuccess,
    String message,
    T data
){
    // 성공(data가 있는 경우)
    public static <T> CommonResponse<T> success(String message, T data){
        return new CommonResponse<>(true, message, data);
    }

    // 성공(data가 없는 경우)
    public static CommonResponse<Void> success(String message){
        return new CommonResponse<>(true, message, null);
    }

    // 실패(data가 있는 경우)
    public static <T> CommonResponse<T> fail(String message, T data){
        return new CommonResponse<>(false, message, data);
    }

    // 실패(data가 없는 경우)
    public static CommonResponse<Void> fail(String message){
        return new CommonResponse<>(false, message, null);
    }

}
