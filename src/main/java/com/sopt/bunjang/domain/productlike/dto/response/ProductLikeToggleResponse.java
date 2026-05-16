package com.sopt.bunjang.domain.productlike.dto.response;

import com.sopt.bunjang.domain.product.entity.Product;
import io.swagger.v3.oas.annotations.media.Schema;

public record ProductLikeToggleResponse(

        @Schema(example = "1")
        Long productId,

        @Schema(example = "true")
        Boolean isLiked,

        @Schema(example = "13")
        Integer likeCount
) {

    public static ProductLikeToggleResponse of(Product product, boolean isLiked) {
        return new ProductLikeToggleResponse(
                product.getId(),
                isLiked,
                product.getLikeCount()
        );
    }
}
