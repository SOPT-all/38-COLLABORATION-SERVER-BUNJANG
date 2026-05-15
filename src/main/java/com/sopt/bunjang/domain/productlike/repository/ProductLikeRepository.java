package com.sopt.bunjang.domain.productlike.repository;

import com.sopt.bunjang.domain.productlike.entity.ProductLike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductLikeRepository extends JpaRepository<ProductLike, Long> {

    // 유저가 상품에 대해서 좋아요를 눌렀는 지 여부
    boolean existsByUserIdAndProductId(Long userId, Long productId);
}
