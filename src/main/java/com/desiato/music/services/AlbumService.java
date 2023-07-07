package com.desiato.music.services;

import com.desiato.music.models.Album;
import com.desiato.music.models.Review;
import com.desiato.music.repositories.AlbumRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Service
public class AlbumService {

    @Autowired
    private AlbumRepository albumRepository;


    // Get all the albums
    public List<Album> allAlbums(){
        return albumRepository.findAll();
    }

    // in the returned value is null we need to use the Optional class
    public Optional<Album> albumById(String id){
        return albumRepository.findAlbumByMusicBrainzId(id);
    }

    public Optional<Album> albumByMusicBrainzId(String musicBrainzId){
        return albumRepository.findAlbumByMusicBrainzId(musicBrainzId);
    }

    public Album save(Album album) {
        return albumRepository.save(album);
    }


    public void calculateNewRating(Album album) {
        List<Review> reviews = album.getReviews();  // updated
        double totalRating = 0;

        // Sum the ratings of all reviews
        for (Review review : reviews) {
            totalRating += review.getAlbumRating();
        }

        // Calculate average rating and round to nearest whole number
        double averageRating = totalRating / reviews.size();
        int roundedAverageRating = (int) Math.round(averageRating);

        // Update the album's overall rating
        album.setOverallRating(roundedAverageRating);

        // Update the average rating
        album.setAverageRating(averageRating);

        // Update the number of ratings
        album.setNumberOfRatings(reviews.size());

        // update the 'rating' field
        if (!reviews.isEmpty()) {
            album.setRating(reviews.get(reviews.size() - 1).getAlbumRating());
        }
    }

    public List<Album> search(String q) {
        return albumRepository.findByTitleContainingIgnoreCase(q);
    }

}
