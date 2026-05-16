package com.sopt.bunjang.domain.product.dto.response;

import java.util.List;

public record PaymentCompleteResponse(
        BoughtTogetherProducts boughtTogetherProducts
) {
    public record BoughtTogetherProducts(
            List<ProductSimpleCardResponse> products
    ) {}
}