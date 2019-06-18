package com.group.milan.debate.debate.ActivityUserProfile.my_squard.model.StatusPojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RemoveUserPojo {
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
