package com.sopt.bunjang.domain.product.repository;

import com.sopt.bunjang.domain.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    // 같은 판매자의 상품 중, 현재 보고 있는 상품은 제외하고 최신순으로 4개 조회
    List<Product> findTop4BySellerIdAndIdNotOrderByCreatedAtDesc(Long sellerId, Long productId);

    // 상품과 그 상품의 판매자 정보를 한 번에 조회
    @Query("""
        select p
        from Product p
        join fetch p.seller
        where p.id = :productId
    """)
    Optional<Product> findByIdWithSeller(Long productId);
}
