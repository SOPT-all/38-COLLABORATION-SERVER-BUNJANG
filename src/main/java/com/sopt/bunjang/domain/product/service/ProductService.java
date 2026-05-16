package com.sopt.bunjang.domain.product.service;

import com.sopt.bunjang.domain.follow.repository.SellerFollowRepository;
import com.sopt.bunjang.domain.product.dto.response.PaymentCompleteResponse;
import com.sopt.bunjang.domain.product.dto.response.ProductCardResponse;
import com.sopt.bunjang.domain.product.dto.response.ProductDetailResponse;
import com.sopt.bunjang.domain.product.dto.response.ProductInfoResponse;
import com.sopt.bunjang.domain.product.dto.response.ProductSectionResponse;
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
import java.util.stream.IntStream;
import java.util.stream.Stream;
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
        userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        productRepository.findById(productId)
                .orElseThrow(() -> new CustomException(ErrorCode.PRODUCT_NOT_FOUND));

        // BOUGHT_TOGETHER 섹션 상품 조회
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

        return new PaymentCompleteResponse(new PaymentCompleteResponse.BoughtTogetherProducts(productResponses));
    }

    // 상품 섹션 조회
    public ProductSectionResponse getProductSections(Long productId, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        productRepository.findById(productId)
                .orElseThrow(() -> new CustomException(ErrorCode.PRODUCT_NOT_FOUND));

        // RECOMMENDED 상품 조회
        List<Product> recommendedList = productRepository.findBySectionTypeWithLimit(SectionType.RECOMMENDED, 6);

        // RELATED_STYLE 상품 조회
        List<Product> relatedStyleList = productRepository.findBySectionTypeWithLimit(SectionType.RELATED_STYLE, 12);

        // 모든 상품 ID를 합쳐서 찜 여부 한 번에 조회
        List<Long> allProductIds = Stream.of(recommendedList, relatedStyleList)
                .flatMap(List::stream)
                .map(Product::getId)
                .toList();
        Set<Long> likedProductIds = new HashSet<>(
                productLikeRepository.findProductIdsByUserIdAndProductIdIn(userId, allProductIds)
        );

        // remainingCount 계산
        Integer remainingCount = Math.max(0, productRepository.countBySectionType(SectionType.RECOMMENDED) - 6);

        return new ProductSectionResponse(
                buildRecommendedProducts(user.getNickname(), recommendedList, likedProductIds, remainingCount),
                buildRelatedStyleProducts(relatedStyleList, likedProductIds)
        );
    }

    // recommendedProducts 섹션 빌드
    private ProductSectionResponse.RecommendedProducts buildRecommendedProducts(
            String nickname, List<Product> products, Set<Long> likedProductIds, Integer remainingCount) {
        List<ProductCardResponse> productResponses = products.stream()
                .map(p -> new ProductCardResponse(
                        p.getId(),
                        p.getThumbnailUrl(),
                        p.getPrice(),
                        p.getProductName(),
                        p.getCreatedAt(),
                        likedProductIds.contains(p.getId()),
                        p.getLikeCount()
                ))
                .toList();

        return new ProductSectionResponse.RecommendedProducts(nickname, remainingCount, productResponses);
    }

    // relatedStyleProducts 섹션 빌드
    private List<ProductSectionResponse.RelatedStyleSection> buildRelatedStyleProducts(
            List<Product> products, Set<Long> likedProductIds) {
        return IntStream.range(0, 3)
                .mapToObj(i -> {
                    List<Product> group = products.subList(i * 4, Math.min((i + 1) * 4, products.size()));

                    if (group.isEmpty()) {
                        return new ProductSectionResponse.RelatedStyleSection(null, List.of());
                    }

                    // 첫 번째 상품이 배너
                    String bannerThumbnailUrl = group.get(0).getThumbnailUrl();

                    // 나머지 3개가 리스트
                    List<ProductSimpleCardResponse> productResponses = group.subList(1, group.size()).stream()
                            .map(p -> new ProductSimpleCardResponse(
                                    p.getId(),
                                    p.getThumbnailUrl(),
                                    p.getPrice(),
                                    p.getProductName(),
                                    likedProductIds.contains(p.getId())
                            ))
                            .toList();

                    return new ProductSectionResponse.RelatedStyleSection(bannerThumbnailUrl, productResponses);
                })
                .toList();
    }
}
