package com.group.milan.debate.debate.ActivityUserProfile.debate.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Response {
    @SerializedName("user_details")
    @Expose

    private UserDetails userDetails;
    @SerializedName("debates")
    @Expose
    private List<Debate> debates = null;

    public UserDetails getUserDetails() {
        return userDetails;
    }

    public void setUserDetails(UserDetails userDetails) {
        this.userDetails = userDetails;
    }

    public List<Debate> getDebates() {
        return debates;
    }

    public void setDebates(List<Debate> debates) {
        this.debates = debates;
    }

}
