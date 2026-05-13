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

@Tag(name = "홈 화면 조회")
@RestController
@RequestMapping("/api/v1/home")
public class ProductHomeController {

    @Operation(summary = "홈 화면 조회", description = "사용자 ID를 기반으로 홈 화면 상품 섹션을 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "홈 화면 조회 성공",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = """
                                    {
                                      "isSuccess": true,
                                      "message": "홈 화면 조회에 성공했습니다.",
                                      "data": {
                                        "recentCategoryProducts": {
                                          "categoryName": "안경",
                                          "totalCount": 5,
                                          "products": [
                                            {
                                              "productId": 1,
                                              "thumbnailUrl": "https://...",
                                              "price": 100,
                                              "productName": "상품명",
                                              "createdAt": "2025-05-11T00:00:00",
                                              "isLiked": false,
                                              "likeCount": 0
                                            }
                                          ]
                                        },
                                        "similarProducts": {
                                          "products": [
                                            {
                                              "productId": 2,
                                              "thumbnailUrl": "https://...",
                                              "price": 100,
                                              "productName": "상품명",
                                              "isLiked": true
                                            }
                                          ]
                                        },
                                        "adProducts": {
                                          "categoryName": "키덜트",
                                          "products": [
                                            {
                                              "productId": 3,
                                              "thumbnailUrl": "https://...",
                                              "price": 100,
                                              "productName": "상품명",
                                              "createdAt": "2025-05-11T00:00:00",
                                              "isLiked": false,
                                              "likeCount": 0
                                            }
                                          ]
                                        }
                                      }
                                    }
                                    """))),
            @ApiResponse(responseCode = "500", description = "사용자를 찾을 수 없음",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = """
                                    {
                                      "isSuccess": false,
                                      "message": "사용자를 찾을 수 없습니다.",
                                      "data": null
                                    }
                                    """))),
            @ApiResponse(responseCode = "500", description = "서버 내부 오류",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = """
                                    {
                                      "isSuccess": false,
                                      "message": "서버 내부 오류가 발생했습니다.",
                                      "data": null
                                    }
                                    """)))
    })
    @GetMapping
    public ResponseEntity<CommonResponse<Map<String, Object>>> getHome(
            @Parameter(description = "사용자 ID", example = "1", required = true)
            @RequestParam Long userId
    ) {

        return ResponseEntity.ok(CommonResponse.success("홈 화면 조회에 성공했습니다.", null));
    }
}