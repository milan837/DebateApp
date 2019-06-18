package com.group.milan.debate.debate.ActivityDebateProfile.team_a;

import android.content.Context;

import com.google.gson.JsonObject;
import com.group.milan.debate.debate.ActivityDebateProfile.model.AddToSquardPojo;
import com.group.milan.debate.debate.ActivityDebateProfile.team_a.model.TeamAUserPojo;
import com.group.milan.debate.debate.ActivityDebateProfile.team_a.model.TeamUser;
import com.group.milan.debate.debate.ActivityDebateProfile.team_a.model.joinBtn.JoinButtonResponse;
import com.group.milan.debate.debate.ActivityDebateProfile.team_a.model.selectTeam.SelectTeamResponse;


public class TeamAPresenter implements TeamAContract.Presenter {

    TeamAContract.Views views;
    Context context;
    TeamARepository teamARepository;

    public TeamAPresenter(TeamAContract.Views views, Context context) {
        this.views = views;
        this.context = context;
        this.teamARepository=new TeamARepository(this,context);
    }

    @Override
    public void sendDataToApi(JsonObject jsonObject) {
        teamARepository.hitTeamAUserApi(jsonObject);
    }

    @Override
    public void getDataFromApi(TeamAUserPojo teamAUserPojo) {
        views.displayTeamAResponse(teamAUserPojo);
    }

    @Override
    public void joinButtonCheckSendData(JsonObject jsonObject) {
        teamARepository.hitJoinBtnCheckApi(jsonObject);
    }

    @Override
    public void joinButtonCheckGetData(JoinButtonResponse joinButtonResponse) {
        views.displayJoinBtnResponse(joinButtonResponse);
    }

    @Override
    public void selectTeamSendData(JsonObject jsonObject) {
        teamARepository.hitSelectTeamApi(jsonObject);
    }

    @Override
    public void selectTeamGetData(SelectTeamResponse selectTeamResponse) {
        views.displaySelectTeamResponse(selectTeamResponse);
    }

}
