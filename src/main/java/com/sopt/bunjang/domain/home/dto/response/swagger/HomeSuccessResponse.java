package com.sopt.bunjang.domain.home.dto.response.swagger;

import com.sopt.bunjang.domain.home.dto.response.HomeResponse;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "홈 화면 조회 성공 응답")
public record HomeSuccessResponse(
        @Schema(example = "true")
        boolean isSuccess,

        @Schema(example = "HOME_FETCH_SUCCESS")
        String code,

        @Schema(example = "홈 화면 조회에 성공했습니다.")
        String message,

        HomeResponse data
) {}