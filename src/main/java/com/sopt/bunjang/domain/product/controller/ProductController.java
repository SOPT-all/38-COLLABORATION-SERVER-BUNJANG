package com.sopt.bunjang.domain.product.controller;

import com.sopt.bunjang.domain.product.dto.response.ProductDetailResponse;
import com.sopt.bunjang.domain.product.dto.response.swagger.ProductDetailSuccessResponse;
import com.sopt.bunjang.domain.product.service.ProductService;
import com.sopt.bunjang.global.response.CommonResponse;
import com.sopt.bunjang.global.response.SuccessCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "상품 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/products")

public class ProductController {

    private final ProductService productService;

    @Operation(
            summary = "상품 상세 조회",
            description = "상품 상세 페이지 - 상품 상세 정보 + 판매자 정보 조회"
    )

    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "상품 상세 조회 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ProductDetailSuccessResponse.class)
                    )
            ),

            @ApiResponse(
                    responseCode = "400",
                    description = "잘못된 요청입니다.",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = """
                                    {
                                      "isSuccess": false,
                                      "message": "잘못된 요청입니다.",
                                      "data": null
                                    }
                                    """)
                    )
            ),

            @ApiResponse(
                    responseCode = "404",
                    description = "상품 또는 사용자를 찾을 수 없습니다.",
                    content = @Content(
                            mediaType = "application/json",
                            examples = {
                                    @ExampleObject(
                                            name = "존재하지 않는 상품",
                                            value = """
                                                    {
                                                      "isSuccess": false,
                                                      "message": "상품을 찾을 수 없습니다.",
                                                      "data": null
                                                    }
                                                    """
                                    ),
                                    @ExampleObject(
                                            name = "존재하지 않는 사용자",
                                            value = """
                                                    {
                                                      "isSuccess": false,
                                                      "message": "사용자를 찾을 수 없습니다.",
                                                      "data": null
                                                    }
                                                    """
                                    )
                            }
                    )
            ),

            @ApiResponse(
                    responseCode = "500",
                    description = "서버 내부 오류가 발생했습니다.",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = """
                                    {
                                      "isSuccess": false,
                                      "message": "서버 내부 오류가 발생했습니다.",
                                      "data": null
                                    }
                                    """)
                    )
            )

    })

    @GetMapping("/{productId}")
    public ResponseEntity<CommonResponse<ProductDetailResponse>> getProductDetail(
            @Parameter(description = "상품 ID", example = "1", required = true)
            @PathVariable Long productId,
            @Parameter(description = "사용자 ID", example = "1", required = true)
            @RequestParam Long userId
    ) {

        ProductDetailResponse response = productService.getProductDetail(productId, userId);
        return ResponseEntity.ok(
                CommonResponse.success(
                    SuccessCode.PRODUCT_DETAIL_FETCH_SUCCESS.getMessage(), response
                )
        );
    }
}