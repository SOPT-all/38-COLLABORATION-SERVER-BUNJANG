package com.sopt.bunjang.domain.follow.repository;

import com.sopt.bunjang.domain.follow.entity.SellerFollow;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SellerFollowRepository extends JpaRepository<SellerFollow, Long> {

    // 팔로우 되어있는 지 확인
    boolean existsByFollowerIdAndSellerId(Long followerId, Long sellerId);
}
