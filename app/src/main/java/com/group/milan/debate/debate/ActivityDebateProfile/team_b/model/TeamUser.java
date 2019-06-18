package com.group.milan.debate.debate.ActivityDebateProfile.team_b.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TeamUser {

    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("profile_picture")
    @Expose
    private String profilePicture;
    @SerializedName("badges")
    @Expose
    private String badges;
    @SerializedName("total_debates")
    @Expose
    private String totalDebates;
    @SerializedName("level")
    @Expose
    private String level;
    @SerializedName("debate_team_id")
    @Expose
    private String debateTeamId;

    private String status="0";
    public String getStatus(){
        return this.status;
    }

    public void setStatus(String status){
        this.status=status;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public String getBadges() {
        return badges;
    }

    public void setBadges(String badges) {
        this.badges = badges;
    }

    public String getTotalDebates() {
        return totalDebates;
    }

    public void setTotalDebates(String totalDebates) {
        this.totalDebates = totalDebates;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getDebateTeamId() {
        return debateTeamId;
    }

    public void setDebateTeamId(String debateTeamId) {
        this.debateTeamId = debateTeamId;
    }

}
