package com.sopt.bunjang.domain.home.dto.response;

import com.sopt.bunjang.domain.product.dto.response.ProductSimpleCardResponse;
import java.util.List;

public record SimilarProductsResponse(
        List<ProductSimpleCardResponse> products
) {}
