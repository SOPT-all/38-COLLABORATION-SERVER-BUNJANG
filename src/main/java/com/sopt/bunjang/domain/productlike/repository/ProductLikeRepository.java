package com.sopt.bunjang.domain.productlike.repository;

import com.sopt.bunjang.domain.productlike.entity.ProductLike;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProductLikeRepository extends JpaRepository<ProductLike, Long> {

    // 유저가 상품에 대해서 좋아요를 눌렀는 지 여부
    boolean existsByUserIdAndProductId(Long userId, Long productId);

    // 유저가 특정 상품을 찜한 정보 조회
    Optional<ProductLike> findByUserIdAndProductId(Long userId, Long productId);

    // 사용자가 찜한 상품 중 특정 상품 ID 목록에 해당하는 것만 조회
    @Query("select pl.product.id from ProductLike pl where pl.user.id = :userId and pl.product.id in :productIds")
    List<Long> findProductIdsByUserIdAndProductIdIn(
            @Param("userId") Long userId,
            @Param("productIds") List<Long> productIds
    );
}
