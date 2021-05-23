package com.chatapplication.FriendlyChat;

public class userModel {
    public userModel(String name, String uID, String mailId, String number) {
        this.name = name;
        this.uID = uID;
        this.mailId = mailId;
        this.number = number;
    }

    private String name;

    private String uID;

    private String number;

    private String mailId;

    public String getName() {
        return name;
    }

    public String getuID() {
        return uID;
    }

    public String getNumber(){ return number;}

    public String getMailId(){ return mailId;}

    public void setuID(String uID) {
        this.uID = uID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void  setNumber(){
        this.number = number;
    }
    public void setMailId(){
        this.mailId = mailId;
    }
}
