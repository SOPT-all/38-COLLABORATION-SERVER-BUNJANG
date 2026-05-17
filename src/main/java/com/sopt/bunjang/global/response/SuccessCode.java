package com.sopt.bunjang.global.response;

public enum SuccessCode {

    // 공통
    OK("요청이 성공적으로 처리되었습니다."),

    // Screen API
    HOME_FETCH_SUCCESS("홈 화면 조회에 성공했습니다."),
    EVENT_FETCH_SUCCESS("이벤트 화면 조회에 성공했습니다."),
    PRODUCT_DETAIL_FETCH_SUCCESS("상품 상세 화면 조회에 성공했습니다."),
    PRODUCT_SECTION_FETCH_SUCCESS("상품 섹션 조회에 성공했습니다."),
    PAYMENT_COMPLETE_FETCH_SUCCESS("결제 완료 화면 조회에 성공했습니다."),

    // Action API
    PRODUCT_LIKE_TOGGLE_SUCCESS("상품 찜 상태 변경에 성공했습니다."),
    SELLER_FOLLOW_TOGGLE_SUCCESS("판매자 팔로우 상태 변경에 성공했습니다.");

    private final String message;

    SuccessCode(String message) {
        this.message = message;
    }

    public String getCode() { return name(); }

    public String getMessage() {
        return message;
    }
}
