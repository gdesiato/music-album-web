package com.desiato.music.controllers;

import com.desiato.music.models.Album;
import com.desiato.music.models.Review;
import com.desiato.music.services.AlbumService;
import com.desiato.music.services.ReviewService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/albums")
public class AlbumController {

    private static final Logger log = LoggerFactory.getLogger(AlbumController.class);

    @Autowired
    private AlbumService albumService;

    @Autowired
    private ReviewService reviewService;

    @GetMapping
    public String getAllAlbums(Model model) {
        List<Album> albums = albumService.allAlbums();
        model.addAttribute("albums", albums);
        return "albums";
    }

    @GetMapping("/{id}")
    public String getAlbumById(@PathVariable("id") String id, Model model) {
        Optional<Album> optionalAlbum = albumService.albumById(id);
        if(optionalAlbum.isPresent()) {
            Album album = optionalAlbum.get();
            log.info("Album found: " + album.toString());

            List<Review> reviews = album.getReviews();
            if (reviews == null) {
                reviews = new ArrayList<>();
                album.setReviews(reviews);
            }

            reviews = reviews.stream()
                    .filter(Objects::nonNull)  // Ensure there's no null values
                    .collect(Collectors.toList());

            model.addAttribute("album", album);
            model.addAttribute("reviews", reviews); // Pass the review objects as a separate attribute
            return "albumDetail"; // album details
        } else {
            model.addAttribute("error", "The album you're looking for doesn't exist.");
            return "error";  // "error"
        }
    }



    @PostMapping("/{id}/reviews-ratings")
    public ResponseEntity<Album> addReviewAndRating(@PathVariable("id") String musicBrainzId, @RequestBody Review review) {
        Optional<Album> optionalAlbum = albumService.albumById(musicBrainzId);
        if (!optionalAlbum.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        Album album = optionalAlbum.get();
        review = reviewService.save(review);
        album.getReviews().add(review);

        // Update the album's overall rating
        albumService.calculateNewRating(album);

        albumService.save(album);

        return ResponseEntity.ok(album);
    }

}
