package com.it.dnc.keoh.instagram.domain;

public class InstagramCounts extends BaseDomain {

    private String media;
    private String follows;
    private String followed_by;

    public InstagramCounts() {

    }

    public String getMedia() {
        return media;
    }

    public void setMedia(String media) {
        this.media = media;
    }

    public String getFollows() {
        return follows;
    }

    public void setFollows(String follows) {
        this.follows = follows;
    }

    public String getFollowed_by() {
        return followed_by;
    }

    public void setFollowed_by(String followed_by) {
        this.followed_by = followed_by;
    }
}