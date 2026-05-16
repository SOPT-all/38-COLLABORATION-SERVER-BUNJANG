package com.sopt.bunjang.domain.product.dto.response.swagger;

import com.sopt.bunjang.domain.product.dto.response.ProductSectionResponse;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "상품 섹션 조회 성공 응답")
public record ProductSectionSuccessResponse(
        @Schema(example = "true")
        boolean isSuccess,

        @Schema(example = "PRODUCT_SECTION_FETCH_SUCCESS")
        String code,

        @Schema(example = "상품 섹션 조회에 성공했습니다.")
        String message,

        ProductSectionResponse data
) {}