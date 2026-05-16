package com.sopt.bunjang.domain.productlike.dto.response.swagger;

import com.sopt.bunjang.domain.productlike.dto.response.ProductLikeToggleResponse;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "상품 찜 토글 성공 응답")
public record ProductLikeToggleSuccessResponse(

        @Schema(example = "true")
        boolean isSuccess,

        @Schema(example = "PRODUCT_LIKE_TOGGLE_SUCCESS")
        String code,

        @Schema(example = "상품 찜 상태 변경에 성공했습니다.")
        String message,

        ProductLikeToggleResponse data
) {
}
