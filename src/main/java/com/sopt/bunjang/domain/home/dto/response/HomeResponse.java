package com.sopt.bunjang.domain.home.dto.response;

import com.sopt.bunjang.domain.product.dto.response.ProductCardResponse;
import com.sopt.bunjang.domain.product.dto.response.ProductSimpleCardResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

public record HomeResponse(
        RecentCategoryProducts recentCategoryProducts,
        SimilarProducts similarProducts,
        AdProducts adProducts
) {
    public record RecentCategoryProducts(
            @Schema(example = "혁준마")
            String nickname,

            @Schema(example = "안경")
            String categoryName,

            @Schema(example = "200")
            Integer remainingCount,

            List<ProductCardResponse> products
    ) {}

    public record SimilarProducts(
            List<ProductSimpleCardResponse> products
    ) {}

    public record AdProducts(
            @Schema(example = "키덜트")
            String categoryName,

            List<ProductCardResponse> products
    ) {}
}