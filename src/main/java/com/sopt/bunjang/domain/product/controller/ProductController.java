package com.sopt.bunjang.domain.product.controller;

import com.sopt.bunjang.global.response.CommonResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Tag(name = "상품 상세 조회")
@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    @Operation(
            summary = "상품 상세 조회",
            description = "상품 ID와 사용자 ID를 기반으로 상품 상세 정보와 판매자 정보를 조회합니다."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "상품 상세 조회 성공",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = """
                                    {
                                      "isSuccess": true,
                                      "message": "상품 상세 조회에 성공했습니다.",
                                      "data": {
                                        "productId": 1,
                                        "productName": "이펙터 글러스 GLCY",
                                        "price": 210000,
                                        "description": "이펙터 글러스 GLCY컬러 판매합니다\\n니콘 변색렌즈 장착되어있습니다",
                                        "thumbnailUrl": "https://image-url.com/product-thumbnail.jpg",
                                        "imageUrls": [
                                          "https://image-url.com/product-1.jpg",
                                          "https://image-url.com/product-2.jpg"
                                        ],
                                        "category": "안경/선글라스",
                                        "productCondition": "사용감 적음",
                                        "quantity": 1,
                                        "deliveryFee": 4000,
                                        "viewCount": 148,
                                        "likeCount": 7,
                                        "chatCount": 0,
                                        "isLiked": false,
                                        "createdAt": "2026-05-13T12:30:00",
                                        "seller": {
                                          "sellerId": 1,
                                          "nickname": "Zufall",
                                          "rate": 5.0,
                                          "reviewCount": 15,
                                          "salesCount": 26,
                                          "isFollowing": false
                                        }
                                      }
                                    }
                                    """)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "잘못된 요청",
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
                    description = "존재하지 않는 상품",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = """
                                    {
                                      "isSuccess": false,
                                      "message": "존재하지 않는 상품입니다.",
                                      "data": null
                                    }
                                    """)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "존재하지 않는 유저",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = """
                                    {
                                      "isSuccess": false,
                                      "message": "존재하지 않는 유저입니다.",
                                      "data": null
                                    }
                                    """)
                    )
            )
    })
    @GetMapping("/{productId}")
    public ResponseEntity<CommonResponse<Map<String, Object>>> getProductDetail(
            @Parameter(description = "상품 ID", example = "1", required = true)
            @PathVariable Long productId,

            @Parameter(description = "사용자 ID", example = "1", required = true)
            @RequestParam Long userId
    ) {

        return ResponseEntity.ok(
                CommonResponse.success("상품 상세 조회에 성공했습니다.", null)
        );
    }
}