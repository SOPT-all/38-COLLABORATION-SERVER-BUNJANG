package com.sopt.bunjang.domain.home.dto.response;

public record HomeResponse(
        RecentCategoryProductsResponse recentCategoryProducts,
        SimilarProductsResponse similarProducts,
        AdProductsResponse adProducts
) {}