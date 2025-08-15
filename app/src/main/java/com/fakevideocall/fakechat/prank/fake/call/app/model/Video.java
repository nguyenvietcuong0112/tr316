package com.fakevideocall.fakechat.prank.fake.call.app.model;

import java.io.Serializable;

public class Video implements Serializable {
    private String name;
    private int imageResId;
    private int videoResId;
    private String category;

    public Video(String name, int imageResId, int videoResId) {
        this.name = name;
        this.imageResId = imageResId;
        this.videoResId = videoResId;
    }

    public Video(String name, int imageResId, int videoResId, String category) {
        this.name = name;
        this.imageResId = imageResId;
        this.videoResId = videoResId;
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImageResId() {
        return imageResId;
    }

    public void setImageResId(int imageResId) {
        this.imageResId = imageResId;
    }

    public int getVideoResId() {
        return videoResId;
    }

    public void setVideoResId(int videoResId) {
        this.videoResId = videoResId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}