package com.sopt.bunjang.domain.product.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;

// 상품 카드 응답
public record ProductCardResponse(
        @Schema(example = "1")
        Long productId,

        @Schema(example = "https://image-url.com/product-thumbnail.jpg")
        String thumbnailUrl,

        @Schema(example = "210000")
        Long price,

        @Schema(example = "이펙터 코러스 GLCY")
        String productName,

        @Schema(example = "2025-05-11T00:00:00")
        LocalDateTime createdAt,

        @Schema(example = "false")
        Boolean isLiked,

        @Schema(example = "7")
        Integer likeCount
) {}