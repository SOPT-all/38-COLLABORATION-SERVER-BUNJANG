package com.sopt.bunjang.domain.product.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.util.List;

// 판매자 정보
public record SellerInfoResponse(

        @Schema(example = "1")
        Long sellerId,

        @Schema(example = "Zufall")
        String nickname,

        @Schema(example = "5.0")
        BigDecimal rate,

        @Schema(example = "15")
        Integer reviewCount,

        @Schema(example = "26")
        Integer salesCount,

        @Schema(example = "false")
        Boolean isFollowing,

        List<SellerProductResponse> sellerProducts
){
}
