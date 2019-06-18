package com.group.milan.debate.debate.ActivityUserProfile.debate.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserProfilePojo {
    @SerializedName("response")
    @Expose

    private Response response;

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }
}
