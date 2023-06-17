package com.desiato.music.services;

import com.desiato.music.models.Album;
import com.desiato.music.repositories.AlbumRepository;
import org.bson.types.ObjectId;
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
}
