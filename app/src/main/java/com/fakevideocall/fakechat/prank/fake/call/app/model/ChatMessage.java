package com.fakevideocall.fakechat.prank.fake.call.app.model;

public class ChatMessage {
    private String text;
    private boolean isUser;

    public ChatMessage(String text, boolean isUser) {
        this.text = text;
        this.isUser = isUser;
    }

    public String getText() {
        return text;
    }

    public boolean isUser() {
        return isUser;
    }
}