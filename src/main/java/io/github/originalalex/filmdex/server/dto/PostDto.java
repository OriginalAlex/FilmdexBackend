package io.github.originalalex.filmdex.server.dto;

import io.github.originalalex.filmdex.server.dto.annotations.ValidPostBody;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class PostDto {

    @NotNull
    @NotEmpty
    @ValidPostBody
    private String body;

    @NotNull
    @NotEmpty
    private String thread;

    private Long replyingTo; // the id of the post which they are replying to


    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
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

}
