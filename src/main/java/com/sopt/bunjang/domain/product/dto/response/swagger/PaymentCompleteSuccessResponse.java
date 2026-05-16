package com.sopt.bunjang.domain.product.dto.response.swagger;

import com.sopt.bunjang.domain.product.dto.response.PaymentCompleteResponse;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "결제 완료 화면 조회 성공 응답")
public record PaymentCompleteSuccessResponse(
        @Schema(example = "true")
        boolean isSuccess,

        @Schema(example = "PAYMENT_COMPLETE_FETCH_SUCCESS")
        String code,

        @Schema(example = "결제 완료 화면 조회에 성공했습니다.")
        String message,

        PaymentCompleteResponse data
) {}