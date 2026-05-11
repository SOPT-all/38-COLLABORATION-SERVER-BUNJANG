package com.sopt.bunjang.domain.user.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import java.math.BigDecimal;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 20)
    private String nickname;

    @ColumnDefault("0.0")
    @Column(nullable = false, precision = 2, scale = 1)
    private BigDecimal rate;

    @ColumnDefault("0")
    @Column(nullable = false)
    private Integer reviewCount;

    @ColumnDefault("0")
    @Column(nullable = false)
    private Integer salesCount;
}