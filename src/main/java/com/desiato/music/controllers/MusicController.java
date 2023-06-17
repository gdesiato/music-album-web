package com.desiato.music.controllers;

import com.desiato.music.models.Album;
import com.desiato.music.services.AlbumService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/albums")
public class MusicController {

    @Autowired
    private AlbumService albumService;

    /* Instead of returning a String or a List directly, is better to return a ResponseEntity<String>
       In this way it is possible to return a proper status code
     */
    @GetMapping
    public ResponseEntity<List<Album>> getAllAlbums(){
        return new ResponseEntity<List<Album>>(albumService.allAlbums(), HttpStatus.OK);
    }

    // Get an album by its ID
    @GetMapping("/{musicBrainzId}")
    public ResponseEntity<Optional<Album>> getMovieByID(@PathVariable String musicBrainzId){
        return new ResponseEntity<Optional<Album>>(albumService.albumById(musicBrainzId), HttpStatus.OK);

    }
}
