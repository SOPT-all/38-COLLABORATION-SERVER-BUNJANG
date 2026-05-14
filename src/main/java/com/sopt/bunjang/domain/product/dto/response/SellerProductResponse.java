package com.sopt.bunjang.domain.product.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

// 상세 페이지 - 판매자
public record SellerProductResponse(
        @Schema(example = "1")
        Long productId,

        @Schema(example = "https://image-url.com/product-thumbnail.jpg")
        String thumbnailUrl,

        @Schema(example = "이펙터 코러스 GLCY")
        String productName,

        @Schema(example = "210000")
        Long price,

        @Schema(example = "7")
        Integer likeCount
) {
}
