package com.group.milan.debate.debate.ActivityDebateProfile.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddToSquardPojo {

    @SerializedName("status")
    @Expose
    public String status;

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
