package com.group.milan.debate.debate.ActivityDebateProfile.team_a.model.selectTeam;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SelectTeamResponse {
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
