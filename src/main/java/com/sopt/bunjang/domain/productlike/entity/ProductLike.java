package com.sopt.bunjang.domain.productlike.entity;

import com.sopt.bunjang.domain.product.entity.Product;
import com.sopt.bunjang.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(
        uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "product_id"})
)
public class ProductLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    private ProductLike(User user, Product product) {
        this.user = user;
        this.product = product;
    }

    public static ProductLike create(User user, Product product) {
        return new ProductLike(user, product);
    }
}
