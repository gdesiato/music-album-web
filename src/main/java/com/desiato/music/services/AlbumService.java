package com.desiato.music.services;

import com.desiato.music.models.Album;
import com.desiato.music.models.Review;
import com.desiato.music.repositories.AlbumRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AlbumService {
    // Inside this class there are the database access methods

    //repository instantiation
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

    public int calculateNewRating(Album album) {
        List<Review> reviews = album.getReviewIds();
        if (reviews.isEmpty()) {
            return 0; // Default rating when there are no reviews yet
        }

        int totalRating = 0;
        for (Review review : reviews) {
            totalRating += review.getAlbumRating();
        }

        return totalRating / reviews.size();
    }

    public void updateRating(Album album) {
        int newRating = calculateNewRating(album);
        album.setRating(newRating);
        albumRepository.save(album);
    }
}
