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

@Tag(name = "결제 완료 화면")
@RestController
@RequestMapping("/api/v1/products")
public class ProductPaymentController {

    @Operation(
            summary = "결제 완료 화면 조회",
            description = "결제 완료 페이지에서 함께 구매한 상품 추천 목록을 조회합니다."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "결제 완료 화면 조회 성공",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = """
                                    {
                                      "isSuccess": true,
                                      "message": "결제 완료 화면 조회에 성공했습니다.",
                                      "data": {
                                        "boughtTogetherProducts": {
                                          "products": [
                                            {
                                              "productId": 1,
                                              "thumbnailUrl": "https://...",
                                              "price": 100,
                                              "productName": "상품명",
                                              "isLiked": true
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
    @GetMapping("/{productId}/payment-complete")
    public ResponseEntity<CommonResponse<Map<String, Object>>> getPaymentComplete(
            @Parameter(description = "상품 ID", example = "1", required = true)
            @PathVariable Long productId,

            @Parameter(description = "사용자 ID", example = "1", required = true)
            @RequestParam Long userId
    ) {

        return ResponseEntity.ok(
                CommonResponse.success("결제 완료 화면 조회에 성공했습니다.", null)
        );
    }
}