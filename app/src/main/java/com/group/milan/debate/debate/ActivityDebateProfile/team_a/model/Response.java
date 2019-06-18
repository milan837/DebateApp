package com.group.milan.debate.debate.ActivityDebateProfile.team_a.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Response {

    @SerializedName("debate_info")
    @Expose
    private DebateInfo debateInfo;
    @SerializedName("team_users")
    @Expose
    private List<TeamUser> teamUsers = null;

    public DebateInfo getDebateInfo() {
        return debateInfo;
    }

    public void setDebateInfo(DebateInfo debateInfo) {
        this.debateInfo = debateInfo;
    }

    public List<TeamUser> getTeamUsers() {
        return teamUsers;
    }

    public void setTeamUsers(List<TeamUser> teamUsers) {
        this.teamUsers = teamUsers;
    }

}