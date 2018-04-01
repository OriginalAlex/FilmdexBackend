package io.github.originalalex.filmdex.server.database.models;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

@Entity
public class Rating {

    @EmbeddedId
    private RatingKey ratingKey;

    private boolean isUpvote; // true if it's a upvote, else it's a downvote

}
