package io.github.originalalex.filmdex.server.database.models;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "poster_id")
    private User poster;

    private String thread;
    private Long replyingTo; // the id of the post  which this post is replying to
    private String body;
    private Date timePosted;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy="post")
    private List<Rating> ratings;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getPoster() {
        return poster;
    }

    public void setPoster(User poster) {
        this.poster = poster;
    }

    public String getThread() {
        return thread;
    }

    public void setThread(String thread) {
        this.thread = thread;
    }

    public Long getReplyingTo() {
        return replyingTo;
    }

    public void setReplyingTo(Long replyingTo) {
        this.replyingTo = replyingTo;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Date getTimePosted() {
        return timePosted;
    }

    public void setTimePosted(Date timePosted) {
        this.timePosted = timePosted;
    }

}
