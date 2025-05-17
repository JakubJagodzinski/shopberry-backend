package com.example.shopberry.domain.reviews;

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
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final ProductRepository productRepository;
    private final CustomerRepository customerRepository;

    private final ReviewDtoMapper reviewDtoMapper;

    private final Integer MIN_RATING_VALUE = 1;
    private final Integer MAX_RATING_VALUE = 5;
    private final Integer MAX_REVIEW_TEXT_LENGTH = 1_000;

    private final String REVIEW_NOT_FOUND_MESSAGE = "Review not found";
    private final String PRODUCT_NOT_FOUND_MESSAGE = "Product not found";
    private final String CUSTOMER_NOT_FOUND_MESSAGE = "Customer not found";
    private final String RATING_VALUE_CANNOT_BE_NULL_MESSAGE = "Rating value cannot be null";
    private final String RATING_VALUE_OUT_OF_BOUNDS_MESSAGE = "Rating value must be between " + MIN_RATING_VALUE + " and " + MAX_RATING_VALUE;
    private final String REVIEW_TEXT_TOO_LONG_MESSAGE = "Review text cannot exceed " + MAX_REVIEW_TEXT_LENGTH + " characters";

    public ReviewService(ReviewRepository reviewRepository, ProductRepository productRepository, CustomerRepository customerRepository, ReviewDtoMapper reviewDtoMapper) {
        this.reviewRepository = reviewRepository;
        this.productRepository = productRepository;
        this.customerRepository = customerRepository;
        this.reviewDtoMapper = reviewDtoMapper;
    }

    @Transactional
    public List<ReviewResponseDto> getReviewsByProductId(Long productId) throws EntityNotFoundException {
        if (!productRepository.existsById(productId)) {
            throw new EntityNotFoundException(PRODUCT_NOT_FOUND_MESSAGE);
        }

        return reviewDtoMapper.toDtoList(reviewRepository.findByProduct_ProductId(productId));
    }

    @Transactional
    public List<ReviewResponseDto> getReviewsByCustomerId(Long customerId) throws EntityNotFoundException {
        if (!customerRepository.existsById(customerId)) {
            throw new EntityNotFoundException(CUSTOMER_NOT_FOUND_MESSAGE);
        }

        return reviewDtoMapper.toDtoList(reviewRepository.findByCustomer_CustomerId(customerId));
    }

    @Transactional
    public ReviewResponseDto getReviewById(Long reviewId) throws EntityNotFoundException {
        Review review = reviewRepository.findById(reviewId).orElse(null);

        if (review == null) {
            throw new EntityNotFoundException(REVIEW_NOT_FOUND_MESSAGE);
        }

        return reviewDtoMapper.toDto(review);
    }

    @Transactional
    public ReviewResponseDto createReview(CreateReviewRequestDto createReviewRequestDto) throws EntityNotFoundException, IllegalArgumentException {
        Product product = productRepository.findById(createReviewRequestDto.getProductId()).orElse(null);

        if (product == null) {
            throw new EntityNotFoundException(PRODUCT_NOT_FOUND_MESSAGE);
        }

        Customer customer = customerRepository.findById(createReviewRequestDto.getCustomerId()).orElse(null);

        if (customer == null) {
            throw new EntityNotFoundException(CUSTOMER_NOT_FOUND_MESSAGE);
        }

        if (createReviewRequestDto.getRatingValue() == null) {
            throw new IllegalArgumentException(RATING_VALUE_CANNOT_BE_NULL_MESSAGE);
        }

        if (createReviewRequestDto.getRatingValue() < MIN_RATING_VALUE || createReviewRequestDto.getRatingValue() > MAX_RATING_VALUE) {
            throw new IllegalArgumentException(RATING_VALUE_OUT_OF_BOUNDS_MESSAGE);
        }

        if (createReviewRequestDto.getReviewText() != null && createReviewRequestDto.getReviewText().length() > MAX_REVIEW_TEXT_LENGTH) {
            throw new IllegalArgumentException(REVIEW_TEXT_TOO_LONG_MESSAGE);
        }

        Review review = new Review();

        review.setProduct(product);
        review.setCustomer(customer);
        review.setRatingValue(createReviewRequestDto.getRatingValue());

        if (createReviewRequestDto.getReviewText() == null) {
            createReviewRequestDto.setReviewText("");
        } else {
            review.setReviewText(createReviewRequestDto.getReviewText());
        }

        return reviewDtoMapper.toDto(reviewRepository.save(review));
    }

    @Transactional
    public ReviewResponseDto updateReviewById(Long reviewId, UpdateReviewRequestDto updateReviewRequestDto) throws EntityNotFoundException, IllegalArgumentException {
        Review review = reviewRepository.findById(reviewId).orElse(null);

        if (review == null) {
            throw new EntityNotFoundException(REVIEW_NOT_FOUND_MESSAGE);
        }

        if (updateReviewRequestDto.getRatingValue() != null) {
            if (updateReviewRequestDto.getRatingValue() < MIN_RATING_VALUE || updateReviewRequestDto.getRatingValue() > MAX_RATING_VALUE) {
                throw new IllegalArgumentException(RATING_VALUE_OUT_OF_BOUNDS_MESSAGE);
            }

            review.setRatingValue(updateReviewRequestDto.getRatingValue());
        }

        if (updateReviewRequestDto.getReviewText() != null) {
            if (updateReviewRequestDto.getReviewText().length() > MAX_REVIEW_TEXT_LENGTH) {
                throw new IllegalArgumentException(REVIEW_TEXT_TOO_LONG_MESSAGE);
            }

            review.setReviewText(updateReviewRequestDto.getReviewText());
        }

        return reviewDtoMapper.toDto(reviewRepository.save(review));
    }

    @Transactional
    public void deleteReviewById(Long reviewId) throws EntityNotFoundException {
        if (!reviewRepository.existsById(reviewId)) {
            throw new EntityNotFoundException(REVIEW_NOT_FOUND_MESSAGE);
        }

        reviewRepository.deleteById(reviewId);
    }

    @Transactional
    public void deleteReviewsByCustomerId(Long customerId) throws EntityNotFoundException {
        if (!customerRepository.existsById(customerId)) {
            throw new EntityNotFoundException(CUSTOMER_NOT_FOUND_MESSAGE);
        }

        reviewRepository.deleteByCustomer_CustomerId(customerId);
    }

    @Transactional
    public void deleteReviewsByProductId(Long productId) throws EntityNotFoundException {
        if (!productRepository.existsById(productId)) {
            throw new EntityNotFoundException(PRODUCT_NOT_FOUND_MESSAGE);
        }

        reviewRepository.deleteByProduct_ProductId(productId);
    }

}
