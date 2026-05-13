package com.sopt.bunjang.domain.product.entity;

import com.sopt.bunjang.domain.product.enums.Category;
import com.sopt.bunjang.domain.product.enums.ProductCondition;
import com.sopt.bunjang.domain.product.enums.SectionType;
import com.sopt.bunjang.domain.user.entity.User;
import com.sopt.bunjang.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Product extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seller_id", nullable = false)
    private User seller;

    @Column(nullable = false, length = 100)
    private String productName;

    @Column(nullable = false)
    private Long price;

    @Column(nullable = false, length = 2500)
    private String description;

    @Column(nullable = false)
    private String thumbnailUrl;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProductCondition productCondition;

    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = false)
    private Integer deliveryFee;

    @ColumnDefault("0")
    @Column(nullable = false)
    private Integer viewCount = 0;

    @ColumnDefault("0")
    @Column(nullable = false)
    private Integer chatCount = 0;

    @ColumnDefault("0")
    @Column(nullable = false)
    private Integer likeCount = 0;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Category category;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SectionType sectionType;
}
