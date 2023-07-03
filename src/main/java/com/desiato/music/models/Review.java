package com.desiato.music.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "reviews")
public class Review {

    @Id
    private ObjectId id;
    private String name;
    private String comment;
    private int albumRating;

    public Review(String name, String comment) {
        this.name = name;
        this.comment = comment;
    }
}
