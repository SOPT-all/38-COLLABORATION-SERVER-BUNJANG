package com.sopt.bunjang.domain.product.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

public record ProductSectionResponse(
        RecommendedProducts recommendedProducts,
        List<RelatedStyleSection> relatedStyleProducts
) {
    public record RecommendedProducts(
            @Schema(example = "혁준마")
            String nickname,

            @Schema(example = "353")
            Integer remainingCount,

            List<ProductCardResponse> products
    ) {}

    public record RelatedStyleSection(
            @Schema(example = "https://image-url.com/banner.jpg")
            String bannerThumbnailUrl,

            List<ProductSimpleCardResponse> products
    ) {}
}