package com.sopt.bunjang.global.response;

import io.swagger.v3.oas.annotations.media.Schema;

public record CommonErrorResponse (
        @Schema(example = "false")
        boolean isSuccess,

        @Schema(example = "에러 코드")
        String code,

        @Schema(example = "에러 메시지")
        String message,

        @Schema(nullable = true, example = "null")
        Object data
){

}
