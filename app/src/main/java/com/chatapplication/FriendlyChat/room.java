package com.chatapplication.FriendlyChat;

public class room {
    public room(String room,String uID){
        this.room = room;
        this.uID = uID;
    }

    private  String room;
    private  String uID;

    public String getRoom(){
        return  room;
    }
    public String getuID(){
        return uID;
    }

    public void setRoom(String room){
        this.room = room;
    }

    public void setuID(String uID){
        this.uID = uID;
    }
}
