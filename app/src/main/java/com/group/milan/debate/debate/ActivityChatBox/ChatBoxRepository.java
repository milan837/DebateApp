package com.group.milan.debate.debate.ActivityChatBox;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.group.milan.debate.debate.ActivityChatBox.pojo.ExitChatResponse;
import com.group.milan.debate.debate.ActivityDebateProfile.team_a.model.selectTeam.SelectTeamResponse;
import com.group.milan.debate.debate.Retrofit.DebateApiInstance;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatBoxRepository {
    Context context;
    ChatBoxContract.Presenter presenter;

    public ChatBoxRepository(Context context, ChatBoxContract.Presenter presenter) {
        this.context = context;
        this.presenter = presenter;
    }

    //exit chat box button api
    public void hitExitChatApi(JsonObject jsonObject){
        Call<ExitChatResponse> apiInterface= DebateApiInstance.getActionApiCallInstance().getExitChatData(jsonObject);
        apiInterface.enqueue(new Callback<ExitChatResponse>() {
            @Override
            public void onResponse(Call<ExitChatResponse> call, Response<ExitChatResponse> response) {
                Log.i("milan_code", String.valueOf(response.code()));
                if(response.code() == 200){
                    ExitChatResponse exitChatResponse=response.body();
                    presenter.getDataFromApi(exitChatResponse);
                }else{
                    Log.i("milan_error", String.valueOf(response.code()));
                }
            }

            @Override
            public void onFailure(Call<ExitChatResponse> call, Throwable t) {
                Toast.makeText(context,"faield to hit api",Toast.LENGTH_LONG).show();
                Log.i("milan_faield",t.getMessage());
            }
        });
    }
}
