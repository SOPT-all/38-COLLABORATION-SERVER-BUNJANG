package com.sopt.bunjang.domain.home.dto.response;

import com.sopt.bunjang.domain.product.dto.response.ProductCardResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

public record AdProductsResponse(
        @Schema(example = "키덜트")
        String categoryName,
        List<ProductCardResponse> products
) {}
