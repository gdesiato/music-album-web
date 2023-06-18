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
            return "albumDetail";  // "albumDetail" here is the name of your view template for a single album
        } else {
            model.addAttribute("error", "The album you're looking for doesn't exist.");
            return "error";  // "error" here is the name of your error view template
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

        // Calculate and update the album's rating
        int newRating = albumService.calculateNewRating(album);
        album.setRating(newRating);

        albumService.save(album);

        return ResponseEntity.ok(album);
    }
}
