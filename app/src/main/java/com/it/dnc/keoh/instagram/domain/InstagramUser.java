package com.it.dnc.keoh.instagram.domain;

public class InstagramUser extends BaseDomain {

    private String id;
    private String username;
    private String profile_picture;
    private String full_name;
    private String bio;
    private String website;
    private String is_business;

    private InstagramCounts counts;

    public InstagramUser(){}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getProfile_picture() {
        return profile_picture;
    }

    public void setProfile_picture(String profile_picture) {
        this.profile_picture = profile_picture;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getIs_business() {
        return is_business;
    }

    public void setIs_business(String is_business) {
        this.is_business = is_business;
    }

    public InstagramCounts getCounts() {
        return counts;
    }

    public void setCounts(InstagramCounts counts) {
        this.counts = counts;
    }
}