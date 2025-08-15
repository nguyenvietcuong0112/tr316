package com.fakevideocall.fakechat.prank.fake.call.app.model;



import java.io.Serializable;

public class Message implements Serializable {
    private String name;
    private int imageResId;

    public Message (String name, int imageResId) {
        this.name = name;
        this.imageResId = imageResId;
    }

    public String getName() {
        return name;
    }

    public int getImageResId() {
        return imageResId;
    }

}