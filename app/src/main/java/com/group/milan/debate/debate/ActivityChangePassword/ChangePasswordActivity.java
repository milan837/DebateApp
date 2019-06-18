package com.group.milan.debate.debate.ActivityChangePassword;

import android.app.ProgressDialog;
import android.media.Image;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.group.milan.debate.debate.ActivityChangePassword.pojo.ChangePasswordResponse;
import com.group.milan.debate.debate.ActivityChatBox.pojo.ExitChatResponse;
import com.group.milan.debate.debate.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChangePasswordActivity extends AppCompatActivity implements ChangePasswordContract.Views{
    @BindView(R.id.activity_change_password_back_btn)
    ImageView backBtn;

    @BindView(R.id.activity_change_password_edittext_old_pwd)
    EditText oldPassword;

    @BindView(R.id.activity_change_password_edittext_new_pwd)
    EditText newPassword;

    @BindView(R.id.activity_change_password_edittext_re_new_pwd)
    EditText reTypePassword;

    @BindView(R.id.activity_create_new_debate_button_create)
    Button changeButton;

    public static ChangePasswordActivity instance=null;
    ChangePasswordResponse changePasswordResponse;
    ChangePasswordPresenter presenter;
    String userId="1";
    ProgressDialog progressDialog;

    public static ChangePasswordActivity newInstance(){
        return instance;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        ButterKnife.bind(this);
        presenter=new ChangePasswordPresenter(getApplicationContext(),this);

        instance=this;
        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Changing...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                overridePendingTransition(R.anim.enter_from_left,R.anim.exit_to_right);
            }
        });

        //change password
        changeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String getOldPassword=oldPassword.getText().toString().trim();
                String getNewPassword=newPassword.getText().toString().trim();
                String getReNewPassword=reTypePassword.getText().toString().trim();

                if(getOldPassword.equals("")){
                    Toast.makeText(getApplicationContext(),"please enter old password",Toast.LENGTH_LONG).show();
                }else if(getNewPassword.equals("")){
                    Toast.makeText(getApplicationContext(),"please enter new password",Toast.LENGTH_LONG).show();
                }else if(getReNewPassword.equals("")){
                    Toast.makeText(getApplicationContext()," please enter re-new password",Toast.LENGTH_LONG).show();
                }else if(!getNewPassword.equalsIgnoreCase(getNewPassword)){
                    Toast.makeText(getApplicationContext(),"please new password not match",Toast.LENGTH_LONG).show();
                }else {
                    sendDataFromApi(userId,getOldPassword,getNewPassword);
                }
            }
        });

    }

    public void sendDataFromApi(String userId,String oldPassword,String newPassword){
        JsonObject jsonObject=new JsonObject();
        jsonObject.addProperty("old_password",oldPassword);
        jsonObject.addProperty("new_password",newPassword );
        jsonObject.addProperty("user_id",userId);
        presenter.sendDataToApi(jsonObject);
        progressDialog.show();
    }

    @Override
    public void displayResponseData(ChangePasswordResponse changePasswordResponse) {
        progressDialog.hide();
        progressDialog.dismiss();
        this.changePasswordResponse=changePasswordResponse;
        if(changePasswordResponse.getResponse().getStatus().equals("ok")){
            Toast.makeText(getApplicationContext(),"change sucessfully",Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(getApplicationContext(),changePasswordResponse.getResponse().getStatus(),Toast.LENGTH_LONG).show();
        }
    }
}
