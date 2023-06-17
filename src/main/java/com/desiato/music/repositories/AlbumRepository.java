package com.desiato.music.repositories;

import com.desiato.music.models.Album;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

// The repository talks to the database and gets the data back
@Repository
public interface AlbumRepository extends MongoRepository<Album, ObjectId> {

    // find album using the Music Brainz Id
    Optional<Album> findAlbumByMusicBrainzId(String musicBrainzId);

}
