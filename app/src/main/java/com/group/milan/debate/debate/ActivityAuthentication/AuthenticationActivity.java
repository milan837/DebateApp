package com.group.milan.debate.debate.ActivityAuthentication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;


import com.group.milan.debate.debate.ActivityAuthentication.login.LoginFragment;
import com.group.milan.debate.debate.ActivityAuthentication.save_info.SaveInfoFragment;
import com.group.milan.debate.debate.ActivityHome.HomeActivity;
import com.group.milan.debate.debate.R;


public class AuthenticationActivity extends AppCompatActivity {

    public static AuthenticationActivity instance=null;
    String status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication);

        status=getIntent().getStringExtra("status");
        instance=this;

        SharedPreferences sharedPreferences=getSharedPreferences("users",MODE_PRIVATE);
        String userId=sharedPreferences.getString("user_id",null);
        String userName=sharedPreferences.getString("username",null);
        Bundle bundle=new Bundle();
        bundle.putString("user_id",userId);

        if(status == null){

            if(userId != null && userName != null){
                Intent intent=new Intent(AuthenticationActivity.this, HomeActivity.class);
                startActivity(intent);
            }

            LoginFragment loginFragment=new LoginFragment();
            FragmentManager fragmentManager=getSupportFragmentManager();
            FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.authentication_fragment_display_layout,loginFragment,"loginFragment");
            fragmentTransaction.setCustomAnimations(R.anim.enter_from_left,R.anim.exit_to_right);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();


        }else{

            SaveInfoFragment saveInfoFragment=new SaveInfoFragment();
            saveInfoFragment.setArguments(bundle);
            FragmentManager fragmentManager=getSupportFragmentManager();
            FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.authentication_fragment_display_layout,saveInfoFragment,"saveFragment");
            fragmentTransaction.setCustomAnimations(R.anim.enter_from_left,R.anim.exit_to_right);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();

        }

    }

    public static AuthenticationActivity newInstance(){
        return instance;
    }
}
