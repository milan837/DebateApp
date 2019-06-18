package com.group.milan.debate.debate.ActivityAuthentication.login;

import android.app.ProgressDialog;
import android.content.Intent;
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
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.gson.JsonObject;
import com.group.milan.debate.debate.ActivityAuthentication.AuthenticationActivity;
import com.group.milan.debate.debate.ActivityAuthentication.forget_password.ForgetPasswordFragment;
import com.group.milan.debate.debate.ActivityAuthentication.login.model.LoginResponse;
import com.group.milan.debate.debate.ActivityAuthentication.signup.SignUpFragment;
import com.group.milan.debate.debate.ActivityHome.HomeActivity;
import com.group.milan.debate.debate.R;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;


public class LoginFragment extends Fragment implements LoginContract.Views{

    LoginPresenter loginPresenter;

    @BindView(R.id.authentication_login_button_login)
    Button loginButton;

    @BindView(R.id.authentication_login_textview_signup)
    TextView signUpTextView;

    @BindView(R.id.authentication_login_edittext_email)
    EditText email;

    @BindView(R.id.authentication_login_edittext_password)
    EditText password;

    @BindView(R.id.authentication_login_textview_forget_password)
    TextView forgetPasswordTextView;

    @BindView(R.id.authentication_login_button_login_with_fb)
    Button loginWithFbButton;

    LoginButton fbLoginButton;

    ProgressDialog progressDialog;
    CallbackManager callbackManager;

    public static LoginFragment getInstance(){
        LoginFragment loginFragment=new LoginFragment();
        return loginFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FacebookSdk.sdkInitialize(getActivity());
        callbackManager= CallbackManager.Factory.create();

        return inflater.inflate(R.layout.fragment_authentication_login,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this,view);
        fbLoginButton=(LoginButton)getActivity().findViewById(R.id.fb_login_button);
        fbLoginButton.setFragment(this);

        //setting progress dailog
        progressDialog=new ProgressDialog(getActivity());
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage("Please wait..");
        progressDialog.setCancelable(false);

        loginPresenter=new LoginPresenter(this,AuthenticationActivity.newInstance());
        initViews();
    }

    private void initViews(){
        //sign up button
        signUpTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SignUpFragment signUpFragment=new SignUpFragment();
                FragmentManager fragmentManager=getFragmentManager();
                FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
                fragmentTransaction.setCustomAnimations(R.anim.enter_from_right,R.anim.exit_to_left);
                fragmentTransaction.replace(R.id.authentication_fragment_display_layout,signUpFragment,"signupFragment");
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        //forget password textview
        forgetPasswordTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ForgetPasswordFragment forgetPasswordFragment=new ForgetPasswordFragment();
                FragmentManager fragmentManager=getFragmentManager();
                FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
                fragmentTransaction.setCustomAnimations(R.anim.enter_from_right,R.anim.exit_to_left);
                fragmentTransaction.replace(R.id.authentication_fragment_display_layout,forgetPasswordFragment,"forgetPasswordFragment");
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String getEmail=email.getText().toString().trim();
                String getPassword=password.getText().toString().trim();
                if(getEmail.isEmpty()){
                    Toast.makeText(getActivity(),"please enter your email",Toast.LENGTH_LONG).show();
                }else if(getPassword.isEmpty()){
                    Toast.makeText(getActivity(),"please enter your password",Toast.LENGTH_LONG).show();
                }else{
                    progressDialog.show();
                    //validate credential
                    sendDataToApi(getEmail,getPassword);
                }
            }
        });

        loginWithFbButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fbLoginButton.performClick();
            }
        });

        //facebook login button
        fbLoginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Toast.makeText(getActivity(),"login sucess",Toast.LENGTH_LONG).show();
                Log.i("milan_fb",loginResult.getAccessToken().getUserId().toString());
                Log.i("milan_fb",loginResult.getAccessToken().getToken().toString());
                getUserData(loginResult);
            }

            @Override
            public void onCancel() {
                Toast.makeText(getActivity(),"login cancle",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(getActivity(),"login error",Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode,resultCode,data);
    }

    //graph api for gatting data

    public void getUserData(final LoginResult loginResult){
        GraphRequest graphRequest=GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {
                Log.i("response_fb", String.valueOf(response));
                try {
                    String facebookId=loginResult.getAccessToken().getUserId();
                    String facebookToken=loginResult.getAccessToken().getToken();
                   // String email=response.getJSONObject().getString("email");
                    String username=response.getJSONObject().getString("name");
                    String imageUrl="https://graph.facebook.com/"+facebookId+"/picture?height=250&width=250&migration_overrides=%7Boctober_2012%3Atrue%7D";
                    /*
                        code for sending the data to repository
                    */
                    JsonObject jsonObject=new JsonObject();
                    jsonObject.addProperty("facebook_id",facebookId);
                    jsonObject.addProperty("username",username);
                    jsonObject.addProperty("image_url",imageUrl);

                    progressDialog.show();
                    loginPresenter.sendFbDataToApi(jsonObject,progressDialog);


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,email,gender,birthday");
        graphRequest.setParameters(parameters);
        graphRequest.executeAsync();
    }


    private void sendDataToApi(String email, String password){
        JsonObject jsonObject=new JsonObject();
        jsonObject.addProperty("email",email);
        jsonObject.addProperty("password",password);
        //sending data to presenter layer
        loginPresenter.sendDataToApi(jsonObject,progressDialog);
    }

    @Override
    public void displayResponseData(String response) {
        progressDialog.dismiss();
        if(response.equals("ok")){
                AuthenticationActivity.newInstance().finish();
                Intent intent=new Intent(AuthenticationActivity.newInstance(), HomeActivity.class);
                AuthenticationActivity.newInstance().startActivity(intent);
        }else{
            Toast.makeText(AuthenticationActivity.newInstance(),response,Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void displayFbResponseData(LoginResponse loginResponse) {
        progressDialog.dismiss();
        if(loginResponse.getResponse().getStatus().equals("ok")){
            Intent intent=new Intent(AuthenticationActivity.newInstance(), HomeActivity.class);
            AuthenticationActivity.newInstance().startActivity(intent);
            AuthenticationActivity.newInstance().finish();
        }else{
            Toast.makeText(getActivity(),loginResponse.getResponse().getStatus(),Toast.LENGTH_LONG).show();
        }
    }
}
