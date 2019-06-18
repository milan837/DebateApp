package com.group.milan.debate.debate.ActivityDebateProfile.team_a.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DebateInfo {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("hash_tags")
    @Expose
    private String hashTags;
    @SerializedName("time")
    @Expose
    private String time;
    @SerializedName("team_a_id")
    @Expose
    private String teamAId;
    @SerializedName("team_a_name")
    @Expose
    private String teamAName;
    @SerializedName("team_a_total_users")
    @Expose
    private String teamATotalUsers;
    @SerializedName("team_b_id")
    @Expose
    private String teamBId;
    @SerializedName("team_b_name")
    @Expose
    private String teamBName;
    @SerializedName("team_b_total_users")
    @Expose
    private String teamBTotalUsers;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getHashTags() {
        return hashTags;
    }

    public void setHashTags(String hashTags) {
        this.hashTags = hashTags;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTeamAName() {
        return teamAName;
    }

    public void setTeamAName(String teamAName) {
        this.teamAName = teamAName;
    }

    public String getTeamATotalUsers() {
        return teamATotalUsers;
    }

    public void setTeamATotalUsers(String teamATotalUsers) {
        this.teamATotalUsers = teamATotalUsers;
    }

    public String getTeamBName() {
        return teamBName;
    }

    public void setTeamBName(String teamBName) {
        this.teamBName = teamBName;
    }

    public String getTeamBTotalUsers() {
        return teamBTotalUsers;
    }

    public void setTeamBTotalUsers(String teamBTotalUsers) {
        this.teamBTotalUsers = teamBTotalUsers;
    }

    public void setTeamAId(String teamAId) {
        this.teamAId = teamAId;
    }

    public void setTeamBId(String teamBId) {
        this.teamBId = teamBId;
    }

    public String getTeamAId() {
        return teamAId;
    }

    public String getTeamBId() {
        return teamBId;
    }
}