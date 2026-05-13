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

@Tag(name = "상품 상세 하단 섹션")
@RestController
@RequestMapping("/api/v1/products")
public class ProductSectionController {

    @Operation(
            summary = "상품 상세 하단 섹션 조회",
            description = "상품 상세 페이지의 좋아할 만한 상품과 같은 스타일의 다른 상품 목록을 조회합니다."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "상품 상세 화면 조회 성공",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = """
                                    {
                                      "isSuccess": true,
                                      "message": "상품 상세 화면 조회에 성공했습니다.",
                                      "data": {
                                        "recommendedProducts": {
                                          "nickname": "혁준마",
                                          "totalCount": 357,
                                          "products": [
                                            {
                                              "productId": 1,
                                              "thumbnailUrl": "https://...",
                                              "price": 690000,
                                              "productName": "상품명",
                                              "createdAt": "2025-05-11T00:00:00",
                                              "isLiked": false,
                                              "likeCount": 0
                                            }
                                          ]
                                        },
                                        "relatedStyleProducts": {
                                          "products": [
                                            {
                                              "productId": 2,
                                              "thumbnailUrl": "https://...",
                                              "price": 690000,
                                              "productName": "상품명",
                                              "createdAt": "2025-05-11T00:00:00",
                                              "isLiked": false,
                                              "likeCount": 0
                                            }
                                          ]
                                        }
                                      }
                                    }
                                    """)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "존재하지 않는 사용자 ID",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = """
                                    {
                                      "isSuccess": false,
                                      "message": "해당 사용자를 찾을 수 없습니다.",
                                      "data": null
                                    }
                                    """)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "존재하지 않는 상품 ID",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = """
                                    {
                                      "isSuccess": false,
                                      "message": "해당 상품을 찾을 수 없습니다.",
                                      "data": null
                                    }
                                    """)
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "서버 내부 오류",
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
    @GetMapping("/{productId}/sections")
    public ResponseEntity<CommonResponse<Map<String, Object>>> getProductSections(
            @Parameter(description = "상품 ID", example = "1", required = true)
            @PathVariable Long productId,

            @Parameter(description = "사용자 ID", example = "1", required = true)
            @RequestParam Long userId
    ) {

        return ResponseEntity.ok(
                CommonResponse.success("상품 상세 화면 조회에 성공했습니다.", null)
        );
    }
}