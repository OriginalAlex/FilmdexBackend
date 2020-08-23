package io.github.originalalex.filmdex.server.dto;


import com.fasterxml.jackson.annotation.JsonProperty;

public class RatingDTO {

    private long postID;

    @JsonProperty
    private boolean isUpvote;

    public long getPostID() {
        return postID;
    }

    public boolean isUpvote() {
        return this.isUpvote;
    }

}
