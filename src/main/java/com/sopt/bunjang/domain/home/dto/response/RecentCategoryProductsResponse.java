package com.sopt.bunjang.domain.home.dto.response;

import com.sopt.bunjang.domain.product.dto.response.ProductCardResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

public record RecentCategoryProductsResponse(
        @Schema(example = "혁준마")
        String nickname,

        @Schema(example = "안경")
        String categoryName,

        @Schema(example = "200")
        Integer remainingCount,
        List<ProductCardResponse> products
) {}