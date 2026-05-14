package com.sopt.bunjang.domain.product.dto.response.swagger;

import com.sopt.bunjang.domain.product.dto.response.ProductDetailResponse;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "상품 상세 조회 성공 응답")

public record ProductDetailSuccessResponse(

        @Schema(example = "true")
        boolean isSuccess,

        @Schema(example = "상품 상세 조회에 성공했습니다.")
        String message,

        ProductDetailResponse data
) {
}
