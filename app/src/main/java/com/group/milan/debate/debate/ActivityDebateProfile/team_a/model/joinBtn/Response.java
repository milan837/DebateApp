package com.group.milan.debate.debate.ActivityDebateProfile.team_a.model.joinBtn;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Response {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("debate_team_id")
    @Expose
    private String debateTeamId;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDebateTeamId() {
        return debateTeamId;
    }

    public void setDebateTeamId(String debateTeamId) {
        this.debateTeamId = debateTeamId;
    }

}