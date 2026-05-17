package com.sopt.bunjang.domain.product.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;


public record ProductDetailResponse(
        @Schema(description = "상품 정보")
        ProductInfoResponse product,

        @Schema(description = "판매자 정보")
        SellerInfoResponse seller
) {
}
