package com.group.milan.debate.debate.ActivityAuthentication.verification.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
public class Response {

    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("user_id")
    @Expose
    private String userId;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}

