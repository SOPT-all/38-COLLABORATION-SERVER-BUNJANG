package com.sopt.bunjang.domain.product.service;

import com.sopt.bunjang.domain.follow.repository.SellerFollowRepository;
import com.sopt.bunjang.domain.product.dto.response.ProductDetailResponse;
import com.sopt.bunjang.domain.product.dto.response.ProductInfoResponse;
import com.sopt.bunjang.domain.product.dto.response.SellerInfoResponse;
import com.sopt.bunjang.domain.product.dto.response.SellerProductResponse;
import com.sopt.bunjang.domain.product.entity.Product;
import com.sopt.bunjang.domain.product.entity.ProductImage;
import com.sopt.bunjang.domain.product.repository.ProductImageRepository;
import com.sopt.bunjang.domain.product.repository.ProductRepository;
import com.sopt.bunjang.domain.productlike.repository.ProductLikeRepository;
import com.sopt.bunjang.domain.user.entity.User;
import com.sopt.bunjang.domain.user.repository.UserRepository;
import com.sopt.bunjang.global.exception.CustomException;
import com.sopt.bunjang.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductImageRepository productImageRepository;
    private final UserRepository userRepository;
    private final ProductLikeRepository productLikeRepository;
    private final SellerFollowRepository sellerFollowRepository;

    public ProductDetailResponse getProductDetail(Long productId, Long userId){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        Product product = productRepository.findByIdWithSeller(productId)
                .orElseThrow(() -> new CustomException(ErrorCode.PRODUCT_NOT_FOUND));

        User seller = product.getSeller();

        List<String> imageUrls = productImageRepository.findAllByProductIdOrderBySortOrderAsc(productId)
                .stream()
                .map(ProductImage::getImageUrl)
                .toList();

        boolean isLiked = productLikeRepository.existsByUserIdAndProductId(user.getId(), product.getId());

        boolean isFollowing = sellerFollowRepository.existsByFollowerIdAndSellerId(user.getId(), seller.getId());

        List<SellerProductResponse> sellerProducts = productRepository
                .findTop4BySellerIdAndIdNotOrderByCreatedAtDesc(seller.getId(), product.getId())
                .stream()
                .map(sellerProduct -> new SellerProductResponse(
                        sellerProduct.getId(),
                        sellerProduct.getThumbnailUrl(),
                        sellerProduct.getProductName(),
                        sellerProduct.getPrice(),
                        sellerProduct.getLikeCount()
                ))
                .toList();

        ProductInfoResponse productInfo = new ProductInfoResponse(
                product.getId(),
                imageUrls,
                product.getProductName(),
                product.getPrice(),
                isLiked,
                product.getLikeCount(),
                product.getViewCount(),
                product.getChatCount(),
                product.getCreatedAt(),
                product.getCategory(),
                product.getProductCondition(),
                product.getQuantity(),
                product.getDescription(),
                product.getDeliveryFee()
        );

        SellerInfoResponse sellerInfo = new SellerInfoResponse(
                seller.getId(),
                seller.getNickname(),
                seller.getRate(),
                seller.getReviewCount(),
                seller.getSalesCount(),
                isFollowing,
                sellerProducts
        );

        return new ProductDetailResponse(productInfo, sellerInfo);
    }
}
