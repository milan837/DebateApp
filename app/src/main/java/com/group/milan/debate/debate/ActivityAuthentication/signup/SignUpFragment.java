package com.group.milan.debate.debate.ActivityAuthentication.signup;

import android.app.ProgressDialog;
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
import com.group.milan.debate.debate.ActivityAuthentication.signup.model.SignUpResponse;
import com.group.milan.debate.debate.ActivityAuthentication.verification.VerificationFragment;
import com.group.milan.debate.debate.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SignUpFragment extends Fragment implements SignUpContract.Views{

    SignUpPresenter signUpPresenter;

    @BindView(R.id.authentication_signup_edittext_email)
    EditText email;

    @BindView(R.id.authentication_signup_edittext_password)
    EditText password;

    @BindView(R.id.authentication_signup_edittext_re_type_password)
    EditText reTypePassword;

    @BindView(R.id.authentication_signup_button_signup)
    Button signButton;

    @BindView(R.id.authentication_signup_button_already_have_acc)
    Button loginBtn;

    String getEmail=null;
    ProgressDialog progressDialog;

    public static SignUpFragment newInstance(){
        return new SignUpFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_authentication_signup,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this,view);

        //setting progress dailog
        progressDialog=new ProgressDialog(getActivity());
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage("Please wait..");
        progressDialog.setCancelable(false);

        signUpPresenter=new SignUpPresenter(this,getActivity());
        initViews();
    }

    private void initViews(){
        signButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getEmail=email.getText().toString().trim();
                String getPassword=password.getText().toString().trim();
                String getRetypePassword=reTypePassword.getText().toString().trim();

                if(getEmail.isEmpty()){
                    Toast.makeText(getActivity(),"please enter your email",Toast.LENGTH_LONG).show();
                }else if(getPassword.isEmpty()){
                    Toast.makeText(getActivity(),"please enter password",Toast.LENGTH_LONG).show();
                }else if(getRetypePassword.isEmpty()){
                    Toast.makeText(getActivity(),"please re-type password",Toast.LENGTH_LONG).show();
                }else if(!getPassword.equals(getRetypePassword)){
                    Toast.makeText(getActivity(),"pssword not match",Toast.LENGTH_LONG).show();
                }else{
                    progressDialog.show();
                    sendDataToApi(getEmail,getPassword);
                }
            }
        });

        //login button
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(getFragmentManager().getBackStackEntryCount() > 0 ){
                    getFragmentManager().popBackStackImmediate();
                }
            }
        });
    }

    public void sendDataToApi(String email,String password){
        JsonObject jsonObject=new JsonObject();
        jsonObject.addProperty("email",email);
        jsonObject.addProperty("password","password");
        signUpPresenter.sendDataToApi(jsonObject,progressDialog);
    }

    @Override
    public void displayResponse(SignUpResponse signUpResponse) {
        progressDialog.dismiss();
        if(signUpResponse.getResponse().getStatus().equals("ok")){

            //sending data to verification fragment
            Bundle bundle=new Bundle();
            bundle.putString("email",signUpResponse.getResponse().getEmail());
            bundle.putString("user_id",signUpResponse.getResponse().getUserId());

            //moving to verification fragment
            VerificationFragment verificationFragment=new VerificationFragment();
            verificationFragment.setArguments(bundle);
            FragmentManager fragmentManager=AuthenticationActivity.newInstance().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
            fragmentTransaction.setCustomAnimations(R.anim.enter_from_right,R.anim.exit_to_left);
            fragmentTransaction.replace(R.id.authentication_fragment_display_layout,verificationFragment,"verificationFragment");
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();

        }else{
            Toast.makeText(AuthenticationActivity.newInstance(),signUpResponse.getResponse().getStatus(),Toast.LENGTH_LONG).show();
        }
    }
}
