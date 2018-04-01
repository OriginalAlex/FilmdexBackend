package io.github.originalalex.filmdex.server.database.models;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class RatingKey implements Serializable {

    private Post post;
    private Long raterId;

}
