package com.group.milan.debate.debate.ActivityAuthentication.verification;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.group.milan.debate.debate.ActivityAuthentication.AuthenticationActivity;
import com.group.milan.debate.debate.ActivityAuthentication.save_info.SaveInfoFragment;
import com.group.milan.debate.debate.ActivityAuthentication.verification.model.VerificationResponse;
import com.group.milan.debate.debate.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class VerificationFragment extends Fragment implements VerificationContract.Views{

    VerificationPresenter verificationPresenter;

    @BindView(R.id.authentication_verification_verify_button)
    Button verifyBtn;

    @BindView(R.id.authentication_verification_resend_button)
    Button resendBtn;

    @BindView(R.id.authentication_verification_edittext_code)
    EditText codeBox;

    //data
    public String email,userId;
    ProgressDialog progressDialog;

    public static VerificationFragment newInstance(){
        return new VerificationFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_authentication_verification,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        email=getArguments().getString("email");
        userId=getArguments().getString("user_id");
        Log.i("milan_ll",email+" "+userId);

        //setting progress dailog
        progressDialog=new ProgressDialog(getActivity());
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage("Please wait..");
        progressDialog.setCancelable(false);

        ButterKnife.bind(this,view);
        verificationPresenter=new VerificationPresenter(this,getActivity());
        initViews();
    }

    private void initViews(){

        verifyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.show();
                String getCode=codeBox.getText().toString().trim();
                if(getCode.isEmpty()){
                    Toast.makeText(getActivity(),"please enter the code",Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(getActivity(),getCode,Toast.LENGTH_LONG).show();
                    sendDataTOApi(email,getCode);
                }
            }
        });
    }

    public void sendDataTOApi(String email,String code){
        JsonObject jsonObject=new JsonObject();
        jsonObject.addProperty("email",email);
        jsonObject.addProperty("user_id",userId);
        jsonObject.addProperty("code",code);
        verificationPresenter.sendDataToApi(jsonObject,progressDialog);
    }

    @Override
    public void displayVerificationResponse(VerificationResponse verificationResponse) {
        progressDialog.dismiss();

        if(verificationResponse.getResponse().getStatus().equals("ok")){
            Bundle bundle=new Bundle();
            bundle.putString("user_id",verificationResponse.getResponse().getUserId());

            SaveInfoFragment saveInfoFragment=new SaveInfoFragment();
            saveInfoFragment.setArguments(bundle);

            FragmentManager fragmentManager=AuthenticationActivity.newInstance().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
            fragmentTransaction.setCustomAnimations(R.anim.enter_from_right,R.anim.exit_to_left);
            fragmentTransaction.replace(R.id.authentication_fragment_display_layout,saveInfoFragment,"saveInfoFragment");
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();

        }else{
            Toast.makeText(AuthenticationActivity.newInstance(),verificationResponse.getResponse().getStatus(),Toast.LENGTH_LONG).show();
        }
    }
}
