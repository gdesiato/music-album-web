package com.desiato.music.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "music")
public class Album {

    @Id
    private ObjectId id;
    private String musicBrainzId;
    private String artist;
    private String title;
    private String releaseYear;
    private List<String> genres;
    private List<String> tracks;
    private String cover;
    private List<String> backdrops;
    private double averageRating;
    private int numberOfRatings;
    private int rating;


    @DBRef
    private List<Review> reviewIds;

}
