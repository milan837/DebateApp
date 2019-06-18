package com.group.milan.debate.debate.ActivityUserProfile;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.group.milan.debate.debate.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UserProfileActivity extends AppCompatActivity {

    @BindView(R.id.activity_user_profile_viewpager)
    ViewPager viewpager;

    @BindView(R.id.activity_user_profile_tablayout)
    TabLayout tabLayout;

    public static UserProfileActivity instance=null;
    String userId;

    public static UserProfileActivity newInstance(){
        return instance;
    }

    @BindView(R.id.activity_user_profile_back_btn)
    ImageView backBtn;

    @BindView(R.id.activity_debate_profile_menu_option)
    ImageView optionImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        ButterKnife.bind(this);

        this.instance=this;
        userId=getIntent().getStringExtra("user_id");

        SharedPreferences sharedPreferences=getSharedPreferences("users",MODE_PRIVATE);
        String getUserId=sharedPreferences.getString("user_id",null);

        if(getUserId.equals(userId)){
            optionImage.setVisibility(View.VISIBLE);
        }else{
            optionImage.setVisibility(View.GONE);
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        UserProfileAdapterViewPager adapterViewPager=new UserProfileAdapterViewPager(getSupportFragmentManager(),userId);
        viewpager.setAdapter(adapterViewPager);
        tabLayout.setupWithViewPager(viewpager);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                overridePendingTransition(R.anim.enter_from_left,R.anim.exit_to_right);
            }
        });

    }

}
