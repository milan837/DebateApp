package com.group.milan.debate.debate.ActivityUserProfile.my_squard;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.group.milan.debate.debate.ActivityChatBox.pojo.ExitChatResponse;
import com.group.milan.debate.debate.ActivityUserProfile.my_squard.model.MySquardPojo;
import com.group.milan.debate.debate.ActivityUserProfile.my_squard.model.StatusPojo.RemoveUserPojo;
import com.group.milan.debate.debate.Retrofit.DebateApiInstance;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MySquardRepository {

    MySquardContract.Presenter presenter;
    Context context;

    public MySquardRepository(MySquardContract.Presenter presenter, Context context) {
        this.presenter = presenter;
        this.context = context;
    }

    public MySquardRepository(Context context) {
        this.context = context;
    }

    public void hitMySquardApi(JsonObject jsonObject){
        Call<MySquardPojo> apiInterface= DebateApiInstance.getUserProileApiCallInstance().getMySquardListData(jsonObject);
        apiInterface.enqueue(new Callback<MySquardPojo>() {
            @Override
            public void onResponse(Call<MySquardPojo> call, Response<MySquardPojo> response) {
                if(response.code() == 200){
                    MySquardPojo mySquardPojo=response.body();
                    presenter.getDataFromApi(mySquardPojo);
                }else{
                    Toast.makeText(context,"error code",Toast.LENGTH_LONG).show();
                    Log.i("milan_repo", String.valueOf(response.code()));
                }
            }

            @Override
            public void onFailure(Call<MySquardPojo> call, Throwable t) {
                Toast.makeText(context,"error code",Toast.LENGTH_LONG).show();
                Log.i("milan_repo", t.getMessage());
            }
        });
    }

    //removing user
    public void hitRemoveApi(JsonObject jsonObject){
        Call<RemoveUserPojo> apiInterface=DebateApiInstance.getActionApiCallInstance().getRemoveUserFromSquardData(jsonObject);
        apiInterface.enqueue(new Callback<RemoveUserPojo>() {
            @Override
            public void onResponse(Call<RemoveUserPojo> call, Response<RemoveUserPojo> response) {
                if(response.code() == 200){
                    RemoveUserPojo removeUserPojo=response.body();
                    Toast.makeText(context,removeUserPojo.getResponse().getStatus(),Toast.LENGTH_LONG).show();
                }else{

                    Toast.makeText(context,"error code",Toast.LENGTH_LONG).show();
                    Log.i("milan_repo", String.valueOf(response.code()));
                }
            }

            @Override
            public void onFailure(Call<RemoveUserPojo> call, Throwable t) {
                Toast.makeText(context,"error code",Toast.LENGTH_LONG).show();
                Log.i("milan_repo", t.getMessage());
            }
        });
    }
}
