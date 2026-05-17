package com.sopt.bunjang.domain.product.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

// 간단한 상품 카드 응답 - createdAt, likeCount 미포함
public record ProductSimpleCardResponse(
        @Schema(example = "1")
        Long productId,

        @Schema(example = "https://image-url.com/product-thumbnail.jpg")
        String thumbnailUrl,

        @Schema(example = "210000")
        Long price,

        @Schema(example = "이펙터 코러스 GLCY")
        String productName,

        @Schema(example = "true")
        Boolean isLiked
) {}