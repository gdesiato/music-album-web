package com.desiato.music.controllers;

import com.desiato.music.models.Album;
import com.desiato.music.models.Review;
import com.desiato.music.services.AlbumService;
import com.desiato.music.services.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/albums")
public class AlbumController {

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
            model.addAttribute("album", optionalAlbum.get());
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
        album.getReviewIds().add(review);

        // Update the album's overall rating
        albumService.calculateNewRating(album);

        albumService.save(album);

        return ResponseEntity.ok(album);
    }

    @GetMapping("/{id}/review")
    public String getReviewForm(@PathVariable("id") String id, Model model) {
        Optional<Album> optionalAlbum = albumService.albumById(id);
        if(optionalAlbum.isPresent()) {
            model.addAttribute("album", optionalAlbum.get());
            return "review"; // Show review form
        } else {
            model.addAttribute("error", "The album you're looking for doesn't exist.");
            return "error";  // Show error page
        }
    }

    @PostMapping("/{id}/review")
    public String submitReview(@PathVariable("id") String id,
                               @RequestParam("review") String reviewContent,
                               Model model,
                               RedirectAttributes redirectAttributes) {
        Optional<Album> optionalAlbum = albumService.albumById(id);
        if (optionalAlbum.isEmpty()) {
            model.addAttribute("error", "The album you're looking for doesn't exist.");
            return "error"; // Show error page
        }

        Album album = optionalAlbum.get();
        Review review = new Review(); // Create a new Review object
        review.setComment(reviewContent); // Set the review content
        review = reviewService.save(review); // Save the review
        album.getReviewIds().add(review); // Add the review to the album

        // Update the album's overall rating
        albumService.calculateNewRating(album);

        albumService.save(album); // Save the album

        redirectAttributes.addFlashAttribute("message", "Your review was successfully submitted.");
        return "redirect:/albums/" + id; // Redirect to the album detail page
    }
}
