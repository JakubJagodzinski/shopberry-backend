package com.example.internet_shop.favouriteentries;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/favourite_entries")
public class FavouriteEntryController {

    private final FavouriteEntryService favouriteEntryService;

    public FavouriteEntryController(FavouriteEntryService favouriteEntryService) {
        this.favouriteEntryService = favouriteEntryService;
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<List<FavouriteEntryDto>> getFavouriteEntriesByCustomerId(@PathVariable Long customerId) {
        return ResponseEntity.ok(favouriteEntryService.getFavouriteEntriesByCustomerId(customerId));
    }

    @PostMapping("/")
    public ResponseEntity<FavouriteEntryDto> createFavouriteEntry(@RequestBody CreateFavouriteEntryDto createFavouriteEntryDto) {
        FavouriteEntryDto createdFavouriteEntry = favouriteEntryService.createFavouriteEntry(createFavouriteEntryDto);

        return ResponseEntity.created(URI.create("/api/favourite_entries/" + createdFavouriteEntry.getCustomerId() + "/" + createdFavouriteEntry.getProductId())).body(createdFavouriteEntry);
    }

    @DeleteMapping("/{customerId}/{productId}")
    public ResponseEntity<String> deleteFavouriteEntryByFavouriteEntryId(@PathVariable Long customerId, @PathVariable Long productId) {
        FavouriteEntryId favouriteEntryId = new FavouriteEntryId(productId, customerId);
        favouriteEntryService.deleteFavouriteEntryByFavouriteEntryId(favouriteEntryId);

        return ResponseEntity.ok("Deleted favourite entry with id: " + favouriteEntryId);
    }

}
