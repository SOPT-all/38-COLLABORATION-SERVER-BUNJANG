package com.sopt.bunjang.domain.productlike.service;

import com.sopt.bunjang.domain.product.entity.Product;
import com.sopt.bunjang.domain.product.repository.ProductRepository;
import com.sopt.bunjang.domain.productlike.dto.response.ProductLikeToggleResponse;
import com.sopt.bunjang.domain.productlike.entity.ProductLike;
import com.sopt.bunjang.domain.productlike.repository.ProductLikeRepository;
import com.sopt.bunjang.domain.user.entity.User;
import com.sopt.bunjang.domain.user.repository.UserRepository;
import com.sopt.bunjang.global.exception.CustomException;
import com.sopt.bunjang.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductLikeService {

    private final ProductLikeRepository productLikeRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    @Transactional
    public ProductLikeToggleResponse toggleProductLike(Long productId, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new CustomException(ErrorCode.PRODUCT_NOT_FOUND));

        return productLikeRepository.findByUserIdAndProductId(userId, productId)
                .map(productLike -> unlikeProduct(product, productLike))
                .orElseGet(() -> likeProduct(user, product));
    }

    private ProductLikeToggleResponse likeProduct(User user, Product product) {
        ProductLike productLike = ProductLike.create(user, product);

        productLikeRepository.save(productLike);
        product.increaseLikeCount();

        return ProductLikeToggleResponse.of(product, true);
    }

    private ProductLikeToggleResponse unlikeProduct(Product product, ProductLike productLike) {
        productLikeRepository.delete(productLike);
        product.decreaseLikeCount();

        return ProductLikeToggleResponse.of(product, false);
    }
}
