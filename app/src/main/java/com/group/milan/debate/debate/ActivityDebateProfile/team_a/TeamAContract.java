package com.group.milan.debate.debate.ActivityDebateProfile.team_a;

import com.google.gson.JsonObject;
import com.group.milan.debate.debate.ActivityDebateProfile.model.AddToSquardPojo;
import com.group.milan.debate.debate.ActivityDebateProfile.team_a.model.TeamAUserPojo;
import com.group.milan.debate.debate.ActivityDebateProfile.team_a.model.joinBtn.JoinButtonResponse;
import com.group.milan.debate.debate.ActivityDebateProfile.team_a.model.selectTeam.SelectTeamResponse;

public interface TeamAContract {
    interface Views{
        void displayTeamAResponse(TeamAUserPojo teamAUserPojo);
        void displayJoinBtnResponse(JoinButtonResponse joinButtonResponse);
        void displaySelectTeamResponse(SelectTeamResponse selectTeamResponse);
    }
    interface Presenter{
        void sendDataToApi(JsonObject jsonObject);
        void getDataFromApi(TeamAUserPojo teamAUserPojo);

        //join button check
        void joinButtonCheckSendData(JsonObject jsonObject);
        void joinButtonCheckGetData(JoinButtonResponse joinButtonResponse);

        //select Team api
        void selectTeamSendData(JsonObject jsonObject);
        void selectTeamGetData(SelectTeamResponse selectTeamResponse);
    }
}
