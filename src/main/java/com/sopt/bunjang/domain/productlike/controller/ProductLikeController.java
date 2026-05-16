package com.sopt.bunjang.domain.productlike.controller;

import com.sopt.bunjang.domain.productlike.dto.response.ProductLikeToggleResponse;
import com.sopt.bunjang.domain.productlike.service.ProductLikeService;
import com.sopt.bunjang.global.response.CommonErrorResponse;
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

@Tag(name = "상품 찜 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/products")
public class ProductLikeController {

    private final ProductLikeService productLikeService;

    @Operation(
            summary = "상품 찜 버튼 토글",
            description = """
                    홈 화면, 상품 상세 페이지, 거래 완료 화면에서 사용하는 상품 찜 버튼 토글 API입니다.

                    이미 찜한 상품이면 찜을 취소하고, 찜하지 않은 상품이면 찜을 추가합니다.
                    isLiked는 토글 처리 이후의 최종 찜 상태를 의미합니다.
                    """
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "상품 찜 상태 변경 성공"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "잘못된 요청입니다.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = CommonErrorResponse.class),
                            examples = @ExampleObject(value = """
                                    {
                                      "isSuccess": false,
                                      "code": "INVALID_REQUEST",
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
                            schema = @Schema(implementation = CommonErrorResponse.class),
                            examples = {
                                    @ExampleObject(
                                            name = "존재하지 않는 상품",
                                            value = """
                                                    {
                                                      "isSuccess": false,
                                                      "code": "PRODUCT_NOT_FOUND",
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
                                                      "code": "USER_NOT_FOUND",
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
                    description = "서버 내부 오류",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = CommonErrorResponse.class),
                            examples = @ExampleObject(value = """
                                    {
                                      "isSuccess": false,
                                      "code": "INTERNAL_SERVER_ERROR",
                                      "message": "서버 내부 오류가 발생했습니다.",
                                      "data": null
                                    }
                                    """)
                    )
            )
    })
    @PostMapping("/{productId}/like")
    public ResponseEntity<CommonResponse<ProductLikeToggleResponse>> toggleProductLike(
            @Parameter(description = "상품 ID", example = "1", required = true)
            @PathVariable Long productId,
            @Parameter(description = "사용자 ID", example = "1", required = true)
            @RequestParam Long userId
    ) {
        ProductLikeToggleResponse response = productLikeService.toggleProductLike(productId, userId);

        return ResponseEntity.ok(
                CommonResponse.success(SuccessCode.PRODUCT_LIKE_TOGGLE_SUCCESS, response)
        );
    }
}
