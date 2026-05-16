package com.sopt.bunjang.domain.home.service;

import com.sopt.bunjang.domain.home.dto.response.AdProductsResponse;
import com.sopt.bunjang.domain.home.dto.response.HomeResponse;
import com.sopt.bunjang.domain.home.dto.response.RecentCategoryProductsResponse;
import com.sopt.bunjang.domain.home.dto.response.SimilarProductsResponse;
import com.sopt.bunjang.domain.product.dto.response.ProductCardResponse;
import com.sopt.bunjang.domain.product.dto.response.ProductSimpleCardResponse;
import com.sopt.bunjang.domain.product.entity.Product;
import com.sopt.bunjang.domain.product.enums.SectionType;
import com.sopt.bunjang.domain.product.repository.ProductRepository;
import com.sopt.bunjang.domain.productlike.repository.ProductLikeRepository;
import com.sopt.bunjang.domain.user.entity.User;
import com.sopt.bunjang.domain.user.repository.UserRepository;
import com.sopt.bunjang.global.exception.CustomException;
import com.sopt.bunjang.global.exception.ErrorCode;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class HomeService {

    private static final int RECENT_CATEGORY_LIMIT = 4;
    private static final int SIMILAR_LIMIT = 5;
    private static final int AD_LIMIT = 4;

    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final ProductLikeRepository productLikeRepository;

    public HomeResponse getHome(Long userId) {
        // 사용자 존재 여부 검증
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        // 각 섹션별 상품 조회
        List<Product> recentCategoryList = productRepository.findBySectionTypeWithLimit(SectionType.RECENT_CATEGORY, RECENT_CATEGORY_LIMIT);
        List<Product> similarList = productRepository.findBySectionTypeWithLimit(SectionType.SIMILAR_PRODUCT, SIMILAR_LIMIT);
        List<Product> adList = productRepository.findBySectionTypeWithLimit(SectionType.AD, AD_LIMIT);

        // 모든 섹션 상품 ID를 합쳐서 찜 여부 한 번에 조회
        List<Long> allProductIds = Stream.of(recentCategoryList, similarList, adList)
                .flatMap(List::stream)
                .map(Product::getId)
                .toList();
        Set<Long> likedProductIds = new HashSet<>(
                productLikeRepository.findProductIdsByUserIdAndProductIdIn(userId, allProductIds)
        );

        // 전체 개수에서 현재 보여주는 개수를 빼서 더보기 카운트 계산
        Integer remainingCount = Math.max(0, productRepository.countBySectionType(SectionType.RECENT_CATEGORY) - RECENT_CATEGORY_LIMIT);

        return new HomeResponse(
                buildRecentCategoryProducts(user.getNickname(), recentCategoryList, likedProductIds, remainingCount),
                buildSimilarProducts(similarList, likedProductIds),
                buildAdProducts(adList, likedProductIds)
        );
    }

    // recentCategoryProducts 섹션 빌드
    private RecentCategoryProductsResponse buildRecentCategoryProducts(String nickname, List<Product> products, Set<Long> likedProductIds, Integer remainingCount) {
        List<ProductCardResponse> productResponses = toProductCardResponses(products, likedProductIds);
        String categoryName = products.isEmpty() ? null : products.get(0).getCategory().getLabel();
        return new RecentCategoryProductsResponse(nickname, categoryName, remainingCount, productResponses);
    }

    // similarProducts 섹션 빌드
    private SimilarProductsResponse buildSimilarProducts(List<Product> products, Set<Long> likedProductIds) {
        List<ProductSimpleCardResponse> productResponses = toProductSimpleCardResponses(products, likedProductIds);
        return new SimilarProductsResponse(productResponses);
    }

    // adProducts 섹션 빌드
    private AdProductsResponse buildAdProducts(List<Product> products, Set<Long> likedProductIds) {
        List<ProductCardResponse> productResponses = toProductCardResponses(products, likedProductIds);
        String categoryName = products.isEmpty() ? null : products.get(0).getCategory().getLabel();
        return new AdProductsResponse(categoryName, productResponses);
    }

    // Product 리스트 → ProductCardResponse 리스트 변환
    private List<ProductCardResponse> toProductCardResponses(List<Product> products, Set<Long> likedProductIds) {
        return products.stream()
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
    }

    // Product 리스트 → ProductSimpleCardResponse 리스트 변환
    private List<ProductSimpleCardResponse> toProductSimpleCardResponses(List<Product> products, Set<Long> likedProductIds) {
        return products.stream()
                .map(p -> new ProductSimpleCardResponse(
                        p.getId(),
                        p.getThumbnailUrl(),
                        p.getPrice(),
                        p.getProductName(),
                        likedProductIds.contains(p.getId())
                ))
                .toList();
    }
}
