package com.desiato.music.controllers;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.desiato.music.models.Album;
import com.desiato.music.models.Review;
import com.desiato.music.services.AlbumService;
import com.desiato.music.services.ReviewService;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import java.util.*;

@SpringBootTest
public class AlbumControllerTest {

    @Mock
    private AlbumService albumService;

    @Mock
    private ReviewService reviewService;

    @Mock
    private Model model;

    @InjectMocks
    private AlbumController albumController;

    private Album album;

    @BeforeEach
    public void setUp() {
        album = new Album();
        album.setMusicBrainzId("testAlbumId");
    }

    // Test case for getAllAlbums method
    @Test
    public void testGetAllAlbums() {
        when(albumService.allAlbums()).thenReturn(Collections.singletonList(album));
        String view = albumController.getAllAlbums(model);
        verify(model, times(1)).addAttribute("albums", Collections.singletonList(album));
        assertEquals("albums", view);
    }

    // Test case for getAlbumById method
    @Test
    public void testGetAlbumById() {
        when(albumService.albumById(album.getMusicBrainzId())).thenReturn(Optional.of(album));
        String view = albumController.getAlbumById(album.getMusicBrainzId(), model);
        verify(model, times(1)).addAttribute("album", album);
        assertEquals("albumDetail", view);
    }

    // Test case for getAlbumById method when album is not present
    @Test
    public void testGetAlbumByIdNotFound() {
        when(albumService.albumById(album.getMusicBrainzId())).thenReturn(Optional.empty());
        String view = albumController.getAlbumById(album.getMusicBrainzId(), model);
        verify(model, times(1)).addAttribute("error", "The album you're looking for doesn't exist.");
        assertEquals("error", view);
    }

    // Test case for addReviewAndRating method
    @Test
    public void testAddReviewAndRating() {
        ObjectId id = new ObjectId(); // create a new ObjectId for the review
        Review review = new Review();
        review.setId(id);
        when(albumService.albumById(album.getMusicBrainzId())).thenReturn(Optional.of(album));
        when(reviewService.save(any(Review.class))).thenReturn(review);
        ResponseEntity<Album> response = albumController.addReviewAndRating(album.getMusicBrainzId(), review);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(album, response.getBody());
    }

    // Test case for addReviewAndRating method when album is not present
    @Test
    public void testAddReviewAndRatingNotFound() {
        ObjectId id = new ObjectId(); // create a new ObjectId for the review
        Review review = new Review();
        review.setId(id);
        when(albumService.albumById(album.getMusicBrainzId())).thenReturn(Optional.empty());
        ResponseEntity<Album> response = albumController.addReviewAndRating(album.getMusicBrainzId(), review);
        assertEquals(404, response.getStatusCodeValue());
    }
}

