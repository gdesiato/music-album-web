package com.desiato.music.controllers;

import com.desiato.music.models.Album;
import com.desiato.music.models.Review;
import com.desiato.music.services.AlbumService;
import com.desiato.music.services.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@RequestMapping("/reviews")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private AlbumService albumService;

    @PreAuthorize("isAuthenticated()") // to enforce authorization before accessing the review form
    @GetMapping("/create/{albumId}")
    public String getReviewForm(@PathVariable("albumId") String albumId, Model model) {
        Optional<Album> optionalAlbum = albumService.albumById(albumId);
        if(optionalAlbum.isPresent()) {
            model.addAttribute("album", optionalAlbum.get());
            return "review"; // Show review form
        } else {
            model.addAttribute("error", "The album you're looking for doesn't exist.");
            return "error";  // Show error page
        }
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/create/{albumId}")
    public String submitReview(@PathVariable("albumId") String albumId,
                               @ModelAttribute("review") Review review,
                               RedirectAttributes redirectAttributes) {
        Optional<Album> optionalAlbum = albumService.albumById(albumId);
        if (optionalAlbum.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "The album you're looking for doesn't exist.");
            return "redirect:/reviews/create/"+albumId; //redirect back to the creation form
        }

        Album album = optionalAlbum.get();
        review = reviewService.save(review); // Save the review
        album.getReviewIds().add(review); // Add the review to the album

        // Update the album's overall rating
        albumService.calculateNewRating(album);

        albumService.save(album); // Save the album

        redirectAttributes.addFlashAttribute("message", "Your review was successfully submitted.");
        return "redirect:/albums/" + albumId; // Redirect to the album detail page
    }
}
