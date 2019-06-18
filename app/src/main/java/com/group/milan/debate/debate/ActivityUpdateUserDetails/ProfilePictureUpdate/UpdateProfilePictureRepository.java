package com.group.milan.debate.debate.ActivityUpdateUserDetails.ProfilePictureUpdate;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.group.milan.debate.debate.ActivityAuthentication.save_info.model.SaveInfoResponse;
import com.group.milan.debate.debate.Retrofit.DebateApiInstance;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateProfilePictureRepository {
    Context context;
    UpdateProfilePictureContact.Presenter presenter;

    public UpdateProfilePictureRepository(Context context, UpdateProfilePictureContact.Presenter presenter) {
        this.context = context;
        this.presenter = presenter;
    }

    public void hitUpdateProfilePicApi(RequestBody userId, RequestBody username, MultipartBody.Part image, final ProgressDialog progressDialog){
        Call<SaveInfoResponse> apiInterface= DebateApiInstance.getApiCallInstance().getSaveInfoApiData(username,userId,image);
        apiInterface.enqueue(new Callback<SaveInfoResponse>() {
            @Override
            public void onResponse(Call<SaveInfoResponse> call, Response<SaveInfoResponse> response) {
                if(response.code() == 200){
                    SaveInfoResponse saveInfoResponse=response.body();
                    presenter.getDataFromApi(saveInfoResponse);
                }else{
                    progressDialog.hide();
                    progressDialog.dismiss();
                    Toast.makeText(context,"error code",Toast.LENGTH_LONG).show();
                    Log.i("milan_error_code", String.valueOf(response.code()));
                }
            }

            @Override
            public void onFailure(Call<SaveInfoResponse> call, Throwable t) {
                progressDialog.hide();
                progressDialog.dismiss();
                Toast.makeText(context,"error code",Toast.LENGTH_LONG).show();
                Log.i("milan_error code", String.valueOf(t.getMessage()));
            }
        });
    }
}
