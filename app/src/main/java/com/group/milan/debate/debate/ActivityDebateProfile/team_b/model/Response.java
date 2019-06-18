package com.group.milan.debate.debate.ActivityDebateProfile.team_b.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Response {
    @SerializedName("team_user")
    @Expose
    private List<TeamUser> teamUser = null;

    public List<TeamUser> getTeamUser() {
        return teamUser;
    }

    public void setTeamUser(List<TeamUser> teamUser) {
        this.teamUser = teamUser;
    }
}
