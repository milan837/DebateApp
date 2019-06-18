package com.group.milan.debate.debate.ActivityDebateProfile.team_b;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.group.milan.debate.debate.ActivityDebateProfile.model.AddToSquardPojo;
import com.group.milan.debate.debate.ActivityDebateProfile.team_b.model.TeamUser;
import com.group.milan.debate.debate.ActivityDebateProfile.team_b.model.TeamUserPojo;
import com.group.milan.debate.debate.Retrofit.DebateApiInstance;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TeamBRepository {

    TeamBContact.Presenter presenter;
    Context context;

    public TeamBRepository(TeamBContact.Presenter presenter, Context context) {
        this.presenter = presenter;
        this.context = context;
    }

    public void hitTeamBApi(JsonObject jsonObject){
        Call<TeamUserPojo> apiInterface= DebateApiInstance.getDebateApiCallInstance().getTeamBUserData(jsonObject);
        apiInterface.enqueue(new Callback<TeamUserPojo>() {
            @Override
            public void onResponse(Call<TeamUserPojo> call, Response<TeamUserPojo> response) {
                if(response.code() == 200){
                    TeamUserPojo teamUserPojo=response.body();
                    presenter.getDataFromApi(teamUserPojo);
                }else{
                    Toast.makeText(context,"error code",Toast.LENGTH_LONG).show();
                    Log.i("error_code", String.valueOf(response.code()));
                }
            }

            @Override
            public void onFailure(Call<TeamUserPojo> call, Throwable t) {
                Toast.makeText(context,"faield to hit api",Toast.LENGTH_LONG).show();
                Log.i("error_code",t.getMessage());
            }
        });
    }
}
