package com.sopt.bunjang.domain.product.dto.response;

import com.sopt.bunjang.domain.product.enums.Category;
import com.sopt.bunjang.domain.product.enums.ProductCondition;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.List;

// 상품 정보
public record ProductInfoResponse(

        @Schema(example = "1")
        Long productId,

        @ArraySchema(schema = @Schema(example = "https://image-url.com/product.jpg"))
        List<String> imageUrls,

        @Schema(example = "이펙터 코러스 GLCY")
        String productName,

        @Schema(example = "210000")
        Long price,

        @Schema(example = "false")
        Boolean isLiked,

        @Schema(example = "7")
        Integer likeCount,

        @Schema(example = "148")
        Integer viewCount,

        @Schema(example = "0")
        Integer chatCount,

        @Schema(example = "2026-05-13T12:30:00")
        LocalDateTime createdAt,

        @Schema(example = "GLASSES")
        Category category,

        @Schema(example = "LIGHTLY_USED")
        ProductCondition productCondition,

        @Schema(example = "1")
        Integer quantity,

        @Schema(example = "이펙터 코러스 GLCY컬러 판매합니다.")
        String description,

        @Schema(example = "4000")
        Integer deliveryFee
) {
}
