package com.desiato.music.services;

import com.desiato.music.models.Album;
import com.desiato.music.models.Review;
import com.desiato.music.repositories.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private MongoTemplate mongoTemplate;


    public Review createReview(String name, String comment, String musicBrainzId){

        // create and persist a new review
        Review review = reviewRepository.insert(new Review(name, comment));

        // associate the review with one of the movies
        mongoTemplate.update(Album.class)
                // updating the movie where the musicBrainzId matches the musicBrainz Id received from the user
                .matching(Criteria.where("musicBrainzId").is(musicBrainzId))
                .apply(new Update().push("reviewIds").value(review))
                .first();

        return review;
    }

    public Review save(Review review){
        return reviewRepository.save(review);
    }
}
