package io.github.originalalex.filmdex.server.database.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import javax.persistence.*;

@Entity
@IdClass(RatingKey.class)
public class Rating {

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @PrimaryKeyJoinColumn(name = "post_id")
    @JsonBackReference
    private Post post;

    @Id
    private Long raterId;

    private boolean isUpvote;

    public Rating() {

    }

    public void setPost(Post post) {
        this.post = post;
    }

    public void setRaterId(long id) {
        this.raterId = id;
    }

    public void setIsUpvote(boolean t){
        this.isUpvote = isUpvote;
    }

    public Rating(Post post, long raterId, boolean isUpvote) {
        this.post = post;
        this.raterId = raterId;
        this.isUpvote = isUpvote;
    }

    public Post getPost() {
        return post;
    }

    public Long getRaterId() {
        return raterId;
    }

    public boolean isUpvote() {
        return this.isUpvote;
    }

}
