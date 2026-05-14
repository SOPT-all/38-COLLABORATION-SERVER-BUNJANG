package com.sopt.bunjang.domain.product.entity;

import com.sopt.bunjang.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(
        uniqueConstraints = @UniqueConstraint(
                columnNames = {"product_id", "sort_order"}
        )
)
public class ProductImage extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 어떤 상품의 이미지인지
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    // 상품 이미지 URL
    @Column(nullable = false, length = 500)
    private String imageUrl;

    // 이미지 노출 순서
    @Column(nullable = false)
    private Integer sortOrder;
}
