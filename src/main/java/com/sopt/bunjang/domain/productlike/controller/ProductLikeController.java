package com.sopt.bunjang.domain.productlike.controller;

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

@Tag(name = "찜 버튼 토글")
@RestController
@RequestMapping("/api/v1/products")
public class ProductLikeController {

    @Operation(
            summary = "찜 버튼 토글",
            description = "이미 찜한 상품이면 찜을 취소하고, 찜하지 않은 상품이면 찜을 추가합니다."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "상품 찜 상태 변경 성공",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = """
                                    {
                                      "isSuccess": true,
                                      "message": "상품 찜 상태 변경에 성공했습니다.",
                                      "data": {
                                        "productId": 1,
                                        "isLiked": true,
                                        "likeCount": 13
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
                                      "message": "사용자를 찾을 수 없습니다.",
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
                                      "message": "상품을 찾을 수 없습니다.",
                                      "data": null
                                    }
                                    """)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "userId 또는 productId 형식 오류",
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
            )
    })
    @PostMapping("/{productId}/like")
    public ResponseEntity<CommonResponse<Map<String, Object>>> toggleProductLike(
            @Parameter(description = "상품 ID", example = "1", required = true)
            @PathVariable Long productId,

            @Parameter(description = "사용자 ID", example = "1", required = true)
            @RequestParam Long userId
    ) {

        return ResponseEntity.ok(
                CommonResponse.success("상품 찜 상태 변경에 성공했습니다.", null)
        );
    }
}