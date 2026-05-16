package com.sopt.bunjang.domain.product.dto.response;

import java.util.List;

public record BoughtTogetherProductsResponse(
        List<ProductSimpleCardResponse> products
) {}