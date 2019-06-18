package com.group.milan.debate.debate.ActivityUpdateUserDetails.UsernameUpdate;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.group.milan.debate.debate.ActivityUpdateUserDetails.UsernameUpdate.model.UpdateUsernamePojo;
import com.group.milan.debate.debate.R;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.content.Context.MODE_PRIVATE;

public class UpdateUsernameFragment extends Fragment implements UpdateUsernameContact.Views {

    @BindView(R.id.fragment_update_back_button)
    ImageView backButton;

    @BindView(R.id.fragment_update_username_button)
    Button changeButton;

    @BindView(R.id.fragment_update_username_text_box)
    EditText usernameTxt;

    String userId,username,getUsername;
    UpdateUsernamePresenter presenter;
    ProgressDialog progressDialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_update_username_layout,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this,view);
        this.presenter=new UpdateUsernamePresenter(this,getActivity());

        SharedPreferences sharedPreferences=getActivity().getSharedPreferences("users", MODE_PRIVATE);
        userId=sharedPreferences.getString("user_id",null);
        username=sharedPreferences.getString("username",null);
        usernameTxt.setText(username);

        progressDialog=new ProgressDialog(getActivity());
        progressDialog.setMessage("Updating...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);

        initViews();

    }

    public void initViews(){

        changeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getUsername=usernameTxt.getText().toString().trim();
                if(getUsername.isEmpty()){
                    Toast.makeText(getActivity(),"please enter the username",Toast.LENGTH_LONG).show();
                }else{
                    Log.i("milan_log",userId+"=>"+getUsername);
                    sendDataToApi(userId,getUsername);
                }
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
                getActivity().overridePendingTransition(R.anim.enter_from_left,R.anim.exit_to_right);
            }
        });

    }

    public void sendDataToApi(String userId,String username){
        JsonObject jsonObject=new JsonObject();
        jsonObject.addProperty("user_id",userId);
        jsonObject.addProperty("username",username);
        presenter.sendDataToApi(jsonObject);
        progressDialog.show();
    }

    @Override
    public void displayResponseData(UpdateUsernamePojo updateUsernamePojo) {
        progressDialog.dismiss();
        progressDialog.cancel();

        if(updateUsernamePojo.getResponse().getStatus().equals("ok")){

            //saving data to shared preferences;
            SharedPreferences sharedPreferences=getActivity().getSharedPreferences("users",MODE_PRIVATE);
            SharedPreferences.Editor editor=sharedPreferences.edit();
            editor.putString("username",getUsername);
            editor.commit();

            Toast.makeText(getActivity(),"change Sucessfully",Toast.LENGTH_LONG).show();

        }else{
            Toast.makeText(getActivity(),"change Sucessfully",Toast.LENGTH_LONG).show();
        }

    }
}
