package com.group.milan.debate.debate.ActivityDebateProfile.team_b;

import android.content.Context;

import com.google.gson.JsonObject;
import com.group.milan.debate.debate.ActivityDebateProfile.team_b.model.TeamUserPojo;

public class TeamBPresenter implements TeamBContact.Presenter {
    TeamBContact.Views views;
    Context context;
    TeamBRepository teamBRepository;

    public TeamBPresenter(TeamBContact.Views views, Context context) {
        this.views = views;
        this.context = context;
        this.teamBRepository=new TeamBRepository(this,context);
    }

    @Override
    public void sendDataToApi(JsonObject jsonObject) {
        teamBRepository.hitTeamBApi(jsonObject);
    }

    @Override
    public void getDataFromApi(TeamUserPojo teamUserPojo) {
        views.displayResponseData(teamUserPojo);
    }

}
