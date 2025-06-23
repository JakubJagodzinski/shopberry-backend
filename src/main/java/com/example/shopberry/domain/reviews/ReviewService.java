package com.example.shopberry.domain.reviews;

import com.example.shopberry.common.constants.messages.CustomerMessages;
import com.example.shopberry.common.constants.messages.ProductMessages;
import com.example.shopberry.common.constants.messages.ReviewMessages;
import com.example.shopberry.domain.customers.Customer;
import com.example.shopberry.domain.customers.CustomerRepository;
import com.example.shopberry.domain.products.Product;
import com.example.shopberry.domain.products.ProductRepository;
import com.example.shopberry.domain.reviews.dto.CreateReviewRequestDto;
import com.example.shopberry.domain.reviews.dto.ReviewDtoMapper;
import com.example.shopberry.domain.reviews.dto.ReviewResponseDto;
import com.example.shopberry.domain.reviews.dto.UpdateReviewRequestDto;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final ProductRepository productRepository;
    private final CustomerRepository customerRepository;

    private final ReviewDtoMapper reviewDtoMapper;

    @Transactional
    public List<ReviewResponseDto> getProductAllReviews(Long productId) throws EntityNotFoundException {
        if (!productRepository.existsById(productId)) {
            throw new EntityNotFoundException(ProductMessages.PRODUCT_NOT_FOUND);
        }

        return reviewDtoMapper.toDtoList(reviewRepository.findAllByProduct_ProductId(productId));
    }

    @Transactional
    public List<ReviewResponseDto> getCustomerAllReviews(UUID customerId) throws EntityNotFoundException {
        if (!customerRepository.existsById(customerId)) {
            throw new EntityNotFoundException(CustomerMessages.CUSTOMER_NOT_FOUND);
        }

        return reviewDtoMapper.toDtoList(reviewRepository.findAllByCustomer_UserId(customerId));
    }

    @Transactional
    public ReviewResponseDto getReviewById(Long reviewId) throws EntityNotFoundException {
        Review review = reviewRepository.findById(reviewId).orElse(null);

        if (review == null) {
            throw new EntityNotFoundException(ReviewMessages.REVIEW_NOT_FOUND);
        }

        return reviewDtoMapper.toDto(review);
    }

    @Transactional
    public void updateProductRating(Product product) {
        List<Review> productAllReviews = reviewRepository.findAllByProduct_ProductId(product.getProductId());

        Double ratingSum = 0.0;
        Long ratingsCount = Math.max(1, (long) productAllReviews.size());

        for (Review productReview : productAllReviews) {
            ratingSum += productReview.getRatingValue();
        }

        product.setRatingsCount(ratingsCount);
        product.setRatingValue(ratingSum / ratingsCount);

        productRepository.save(product);
    }

    @Transactional
    public ReviewResponseDto createReview(Long productId, CreateReviewRequestDto createReviewRequestDto) throws EntityNotFoundException {
        Product product = productRepository.findById(productId).orElse(null);

        if (product == null) {
            throw new EntityNotFoundException(ProductMessages.PRODUCT_NOT_FOUND);
        }

        Customer customer = customerRepository.findById(createReviewRequestDto.getCustomerId()).orElse(null);

        if (customer == null) {
            throw new EntityNotFoundException(CustomerMessages.CUSTOMER_NOT_FOUND);
        }

        Review review = new Review();

        review.setProduct(product);
        review.setCustomer(customer);
        review.setRatingValue(createReviewRequestDto.getRatingValue());

        if (createReviewRequestDto.getReviewText() != null) {
            review.setReviewText(createReviewRequestDto.getReviewText());
        }

        Review savedReview = reviewRepository.save(review);

        updateProductRating(product);

        return reviewDtoMapper.toDto(savedReview);
    }

    @Transactional
    public ReviewResponseDto updateReviewById(Long reviewId, UpdateReviewRequestDto updateReviewRequestDto) throws EntityNotFoundException {
        Review review = reviewRepository.findById(reviewId).orElse(null);

        if (review == null) {
            throw new EntityNotFoundException(ReviewMessages.REVIEW_NOT_FOUND);
        }

        if (updateReviewRequestDto.getRatingValue() != null) {
            review.setRatingValue(updateReviewRequestDto.getRatingValue());
        }

        if (updateReviewRequestDto.getReviewText() != null) {
            review.setReviewText(updateReviewRequestDto.getReviewText());
        }

        Review savedReview = reviewRepository.save(review);

        updateProductRating(savedReview.getProduct());

        return reviewDtoMapper.toDto(savedReview);
    }

    @Transactional
    public void deleteReviewById(Long reviewId) throws EntityNotFoundException {
        Review review = reviewRepository.findById(reviewId).orElse(null);

        if (review == null) {
            throw new EntityNotFoundException(ReviewMessages.REVIEW_NOT_FOUND);
        }

        Product productFromReview = review.getProduct();

        reviewRepository.deleteById(reviewId);

        updateProductRating(productFromReview);
    }

    @Transactional
    public void deleteCustomerAllReviews(UUID customerId) throws EntityNotFoundException {
        if (!customerRepository.existsById(customerId)) {
            throw new EntityNotFoundException(CustomerMessages.CUSTOMER_NOT_FOUND);
        }

        List<Review> reviews = reviewRepository.findAllByCustomer_UserId(customerId);
        Set<Product> products = new HashSet<>();

        for (Review review : reviews) {
            products.add(review.getProduct());
        }

        reviewRepository.deleteAllByCustomer_UserId(customerId);

        for (Product product : products) {
            updateProductRating(product);
        }
    }

    @Transactional
    public void deleteProductAllReviews(Long productId) throws EntityNotFoundException {
        Product product = productRepository.findById(productId).orElse(null);

        if (product == null) {
            throw new EntityNotFoundException(ProductMessages.PRODUCT_NOT_FOUND);
        }

        reviewRepository.deleteAllByProduct_ProductId(productId);

        updateProductRating(product);
    }

}
