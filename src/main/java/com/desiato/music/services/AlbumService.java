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

    public void calculateNewRating(Album album) {
        int totalRating = album.getRating();
        int numberOfRatings = album.getNumberOfRatings();
        if (numberOfRatings == 0) {
            album.setOverallRating(0); // Default overall rating when there are no ratings yet
        } else {
            double averageRating = (double) totalRating / numberOfRatings;
            album.setOverallRating(averageRating);
        }
    }
}
