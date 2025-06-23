package com.example.shopberry.domain.favouriteproducts;

import com.example.shopberry.auth.access.CheckPermission;
import com.example.shopberry.domain.favouriteproducts.dto.request.AddProductToFavouritesRequestDto;
import com.example.shopberry.domain.favouriteproducts.dto.response.FavouriteProductResponseDto;
import com.example.shopberry.exception.ApiError;
import com.example.shopberry.user.Permission;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Validated
public class FavouriteProductController {

    private final FavouriteProductService favouriteProductService;

    @Operation(summary = "Get customer all favourite products")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Customer favourite products list",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = FavouriteProductResponseDto.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Access denied",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ApiError.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Customer not found",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ApiError.class)
                            )
                    )
            }
    )
    @CheckPermission(Permission.CUSTOMER_FAVOURITE_PRODUCT_READ_ALL)
    @GetMapping("/customers/{customerId}/favourites")
    public ResponseEntity<List<FavouriteProductResponseDto>> getCustomerAllFavourites(@PathVariable UUID customerId) {
        List<FavouriteProductResponseDto> favouriteProductResponseDtoList = favouriteProductService.getCustomerAllFavourites(customerId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(favouriteProductResponseDtoList);
    }

    @Operation(summary = "Add product to customer favourites")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Product added to customer favourites",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = FavouriteProductResponseDto.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Access denied",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ApiError.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Customer not found",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ApiError.class)
                            )
                    )
            }
    )
    @CheckPermission(Permission.CUSTOMER_FAVOURITE_PRODUCT_ADD)
    @PostMapping("/customers/{customerId}/favourites")
    public ResponseEntity<FavouriteProductResponseDto> addProductToCustomerFavourites(@PathVariable UUID customerId, @Valid @RequestBody AddProductToFavouritesRequestDto addProductToFavouritesRequestDto) {
        FavouriteProductResponseDto createdFavouriteProductResponseDto = favouriteProductService.addProductToCustomerFavourites(customerId, addProductToFavouritesRequestDto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .location(URI.create("/api/v1/customers/" + customerId + "/favourites/" + createdFavouriteProductResponseDto.getProduct().getProductId()))
                .body(createdFavouriteProductResponseDto);
    }

    @Operation(summary = "Remove product from customer favourites")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "204",
                            description = "Product removed from customer favourites"
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Access denied",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ApiError.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Customer not found",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ApiError.class)
                            )
                    )
            }
    )
    @CheckPermission(Permission.CUSTOMER_FAVOURITE_PRODUCT_REMOVE)
    @DeleteMapping("/customers/{customerId}/favourites/{productId}")
    public ResponseEntity<Void> removeProductFromCustomerFavourites(@PathVariable UUID customerId, @PathVariable Long productId) {
        favouriteProductService.removeProductFromCustomerFavourites(customerId, productId);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

}
