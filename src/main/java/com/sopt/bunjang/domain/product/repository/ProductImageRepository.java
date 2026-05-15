package com.sopt.bunjang.domain.product.repository;

import com.sopt.bunjang.domain.product.entity.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductImageRepository extends JpaRepository<ProductImage, Long> {

    // 상품 이미지들 가져오기(product_id로 sort_order 기준 오름차순)
    List<ProductImage> findAllByProductIdOrderBySortOrderAsc(Long productId);
}
