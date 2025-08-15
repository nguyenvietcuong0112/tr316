package com.fakevideocall.fakechat.prank.fake.call.app.model;

import java.io.Serializable;

public class ChatTheme implements Serializable {
    private int id;
    private String name;
    private int backgroundRes;
    private int userMessageBg;
    private int responseMessageBg;
    private int textColor;

    public ChatTheme(int id, String name, int backgroundRes,
                     int userMessageBg, int responseMessageBg, int textColor) {
        this.id = id;
        this.name = name;
        this.backgroundRes = backgroundRes;
        this.userMessageBg = userMessageBg;
        this.responseMessageBg = responseMessageBg;
        this.textColor = textColor;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getBackgroundRes() {
        return backgroundRes;
    }

    public int getUserMessageBg() {
        return userMessageBg;
    }

    public int getResponseMessageBg() {
        return responseMessageBg;
    }

    public int getTextColor() {
        return textColor;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBackgroundRes(int backgroundRes) {
        this.backgroundRes = backgroundRes;
    }

    public void setUserMessageBg(int userMessageBg) {
        this.userMessageBg = userMessageBg;
    }

    public void setResponseMessageBg(int responseMessageBg) {
        this.responseMessageBg = responseMessageBg;
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
    }
}
