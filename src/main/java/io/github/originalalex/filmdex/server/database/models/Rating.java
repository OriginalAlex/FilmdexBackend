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

}
