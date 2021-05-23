package com.chatapplication.FriendlyChat;

//Author : Jignesh Baria

public class chat {
    private String text;
    private String name;

    public chat() {
    }

    public chat( String name ,String text) {
        this.text = text;
        this.name = name;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}