package com.sopt.bunjang.domain.home.controller;

import com.sopt.bunjang.domain.home.dto.response.HomeResponse;
import com.sopt.bunjang.domain.home.dto.response.swagger.HomeSuccessResponse;
import com.sopt.bunjang.domain.home.service.HomeService;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "홈 화면 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/home")
public class HomeController {

    private final HomeService homeService;

    @Operation(
            summary = "홈 화면 조회",
            description = "홈 화면 - 카테고리 추천 상품 / 비슷한 상품 / 광고 상품 조회"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "홈 화면 조회 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = HomeSuccessResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "존재하지 않는 사용자",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = CommonErrorResponse.class),
                            examples = @ExampleObject(value = """
                                    {
                                      "isSuccess": false,
                                      "code": "USER_NOT_FOUND",
                                      "message": "사용자를 찾을 수 없습니다.",
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
    @GetMapping
    public ResponseEntity<CommonResponse<HomeResponse>> getHome(
            @Parameter(description = "사용자 ID", example = "1", required = true)
            @RequestParam Long userId
    ) {
        HomeResponse response = homeService.getHome(userId);
        return ResponseEntity.ok(
                CommonResponse.success(SuccessCode.HOME_FETCH_SUCCESS, response)
        );
    }
}