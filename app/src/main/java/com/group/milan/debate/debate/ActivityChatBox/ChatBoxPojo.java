package com.group.milan.debate.debate.ActivityChatBox;

public class ChatBoxPojo {
    public String debate_team_id;
    public String images;
    public String message;
    public boolean private_team_message;
    public long time;
    public String user_id;
    public String user_profile_url;
    public String username;

    public ChatBoxPojo() {
        //default constructor for initilization
    }

    public ChatBoxPojo(String debate_team_id, String images, String message, boolean private_team_message, long time, String user_id, String user_profile_url, String username) {
        this.debate_team_id = debate_team_id;
        this.images = images;
        this.message = message;
        this.private_team_message = private_team_message;
        this.time = time;
        this.user_id = user_id;
        this.user_profile_url = user_profile_url;
        this.username = username;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public void setDebate_team_id(String debate_team_id) {
        this.debate_team_id = debate_team_id;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public void setPrivate_team_message(boolean private_team_message) {
        this.private_team_message = private_team_message;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public void setUser_profile_url(String user_profile_url) {
        this.user_profile_url = user_profile_url;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUser_id() {
        return user_id;
    }

    public String getMessage() {
        return message;
    }

    public String getDebate_team_id() {
        return debate_team_id;
    }

    public String getImages() {
        return images;
    }

    public long getTime() {
        return time;
    }

    public String getUser_profile_url() {
        return user_profile_url;
    }

    public String getUsername() {
        return username;
    }
}
