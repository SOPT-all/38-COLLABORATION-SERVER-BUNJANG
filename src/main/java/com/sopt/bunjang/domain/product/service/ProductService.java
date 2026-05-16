package com.sopt.bunjang.domain.product.service;

import com.sopt.bunjang.domain.follow.repository.SellerFollowRepository;
import com.sopt.bunjang.domain.product.dto.response.BoughtTogetherProductsResponse;
import com.sopt.bunjang.domain.product.dto.response.PaymentCompleteResponse;
import com.sopt.bunjang.domain.product.dto.response.ProductDetailResponse;
import com.sopt.bunjang.domain.product.dto.response.ProductInfoResponse;
import com.sopt.bunjang.domain.product.dto.response.ProductSimpleCardResponse;
import com.sopt.bunjang.domain.product.dto.response.SellerInfoResponse;
import com.sopt.bunjang.domain.product.dto.response.SellerProductResponse;
import com.sopt.bunjang.domain.product.entity.Product;
import com.sopt.bunjang.domain.product.entity.ProductImage;
import com.sopt.bunjang.domain.product.enums.SectionType;
import com.sopt.bunjang.domain.product.repository.ProductImageRepository;
import com.sopt.bunjang.domain.product.repository.ProductRepository;
import com.sopt.bunjang.domain.productlike.repository.ProductLikeRepository;
import com.sopt.bunjang.domain.user.entity.User;
import com.sopt.bunjang.domain.user.repository.UserRepository;
import com.sopt.bunjang.global.exception.CustomException;
import com.sopt.bunjang.global.exception.ErrorCode;
import java.util.HashSet;
import java.util.Set;
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

    // 상품 상세 정보 조회
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

    // 결제 완료 화면 조회
    public PaymentCompleteResponse getPaymentComplete(Long productId, Long userId) {
        // 사용자 존재 여부 검증
        userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        // 상품 존재 여부 검증
        productRepository.findById(productId)
                .orElseThrow(() -> new CustomException(ErrorCode.PRODUCT_NOT_FOUND));

        // BOUGHT_TOGETHER 섹션 상품 조회 (최대 4개)
        List<Product> boughtTogetherList = productRepository.findBySectionTypeWithLimit(SectionType.BOUGHT_TOGETHER, 4);

        // 보이는 상품들 중 사용자가 찜한 상품 ID를 한 번에 조회
        List<Long> productIds = boughtTogetherList.stream()
                .map(Product::getId)
                .toList();
        Set<Long> likedProductIds = new HashSet<>(
                productLikeRepository.findProductIdsByUserIdAndProductIdIn(userId, productIds)
        );

        List<ProductSimpleCardResponse> productResponses = boughtTogetherList.stream()
                .map(p -> new ProductSimpleCardResponse(
                        p.getId(),
                        p.getThumbnailUrl(),
                        p.getPrice(),
                        p.getProductName(),
                        likedProductIds.contains(p.getId())
                ))
                .toList();

        return new PaymentCompleteResponse(new BoughtTogetherProductsResponse(productResponses));
    }
}
