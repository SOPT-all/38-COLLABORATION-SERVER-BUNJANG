package com.sopt.bunjang.domain.product.repository;

import com.sopt.bunjang.domain.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    // SellerId를 기준으로 전체 조회
    List<Product> findAllBySellerId(Long sellerId);
}
