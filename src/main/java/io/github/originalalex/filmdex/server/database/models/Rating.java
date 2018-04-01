package io.github.originalalex.filmdex.server.database.models;

import javax.persistence.*;

@Entity
@IdClass(RatingKey.class)
public class Rating {

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @PrimaryKeyJoinColumn(name = "post_id")
    private Post post;

    @Id
    private Long raterId;

    private boolean isUpvote;

}
