package com.group.milan.debate.debate.ActivityDebateProfile.team_a;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.group.milan.debate.debate.ActivityDebateProfile.model.AddToSquardPojo;
import com.group.milan.debate.debate.ActivityDebateProfile.team_a.model.TeamAUserPojo;
import com.group.milan.debate.debate.ActivityDebateProfile.team_a.model.TeamUser;
import com.group.milan.debate.debate.ActivityDebateProfile.team_a.model.joinBtn.JoinButtonResponse;
import com.group.milan.debate.debate.ActivityDebateProfile.team_a.model.selectTeam.SelectTeamResponse;
import com.group.milan.debate.debate.ActivityDebateProfile.team_b.model.TeamUserPojo;
import com.group.milan.debate.debate.Retrofit.DebateApiInstance;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class  TeamARepository {

    TeamAContract.Presenter presenter;
    Context context;

    public TeamARepository(TeamAContract.Presenter presenter, Context context) {
        this.presenter = presenter;
        this.context = context;
    }

    public TeamARepository(Context context){
        this.context=context;
    }

    public void hitTeamAUserApi(JsonObject jsonObject){
        Call<TeamAUserPojo> apiInterface= DebateApiInstance.getDebateApiCallInstance().getTeamAUsersData(jsonObject);
        apiInterface.enqueue(new Callback<TeamAUserPojo>() {
            @Override
            public void onResponse(Call<TeamAUserPojo> call, Response<TeamAUserPojo> response) {
                if(response.code() == 200){
                    TeamAUserPojo teamAUserPojo=response.body();
                    presenter.getDataFromApi(teamAUserPojo);
                }else{
                    Toast.makeText(context,"error code",Toast.LENGTH_LONG).show();
                    Log.i("milan_error", String.valueOf(response.code()));
                }
            }

            @Override
            public void onFailure(Call<TeamAUserPojo> call, Throwable t) {
                Toast.makeText(context,"faield to hit api",Toast.LENGTH_LONG).show();
                Log.i("milan_faield",t.getMessage());
            }
        });
    }


    //user action addto squard api
    public void hitAddToSquardApi(JsonObject jsonObject){
        Call<AddToSquardPojo> apiInterface=DebateApiInstance.getActionApiCallInstance().getAddToSquardBtnData(jsonObject);
        apiInterface.enqueue(new Callback<AddToSquardPojo>() {
            @Override
            public void onResponse(Call<AddToSquardPojo> call, Response<AddToSquardPojo> response) {
                if(response.code() == 200){
                    AddToSquardPojo addToSquardPojo=response.body();
                    Toast.makeText(context,addToSquardPojo.getStatus(),Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(context,"error code",Toast.LENGTH_LONG).show();
                    Log.i("milan_error", String.valueOf(response.code()));
                }
            }

            @Override
            public void onFailure(Call<AddToSquardPojo> call, Throwable t) {
                Toast.makeText(context,"faield to hit api",Toast.LENGTH_LONG).show();
                Log.i("milan_faield",t.getMessage());
            }

        });
    }

    //join debate bttn api
    public void hitJoinBtnCheckApi(JsonObject jsonObject){
        Call<JoinButtonResponse> apiInterface=DebateApiInstance.getActionApiCallInstance().getJoinButtonData(jsonObject);
        apiInterface.enqueue(new Callback<JoinButtonResponse>() {
            @Override
            public void onResponse(Call<JoinButtonResponse> call, Response<JoinButtonResponse> response) {
                Log.i("milan_code", String.valueOf(response.code()));
                if(response.code() == 200){
                    JoinButtonResponse joinButtonResponse=response.body();
                    presenter.joinButtonCheckGetData(joinButtonResponse);
                }else{
                    Log.i("milan_error", String.valueOf(response.code()));
                }
            }

            @Override
            public void onFailure(Call<JoinButtonResponse> call, Throwable t) {
                Toast.makeText(context,"faield to hit api",Toast.LENGTH_LONG).show();
                Log.i("milan_faield",t.getMessage());
            }
        });
    }

    //join button select team api
    public void hitSelectTeamApi(JsonObject jsonObject){
        Call<SelectTeamResponse> apiInterface=DebateApiInstance.getActionApiCallInstance().getSelectTeamData(jsonObject);
        apiInterface.enqueue(new Callback<SelectTeamResponse>() {
            @Override
            public void onResponse(Call<SelectTeamResponse> call, Response<SelectTeamResponse> response) {
                Log.i("milan_code", String.valueOf(response.code()));
                if(response.code() == 200){
                    SelectTeamResponse selectTeamResponse=response.body();
                    presenter.selectTeamGetData(selectTeamResponse);
                }else{
                    Log.i("milan_error", String.valueOf(response.code()));
                }
            }

            @Override
            public void onFailure(Call<SelectTeamResponse> call, Throwable t) {
                Toast.makeText(context,"faield to hit api",Toast.LENGTH_LONG).show();
                Log.i("milan_faield",t.getMessage());
            }
        });
    }


}
