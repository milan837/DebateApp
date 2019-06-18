package com.group.milan.debate.debate.ActivityDebateProfile;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.group.milan.debate.debate.ActivityChatBox.ChatBoxActivity;
import com.group.milan.debate.debate.R;


import butterknife.BindView;
import butterknife.ButterKnife;

public class DebateProfileActivity extends AppCompatActivity implements DebateProfileContract.Views {

    @BindView(R.id.activity_debate_profile_viewpager)
    ViewPager viewPager;

    @BindView(R.id.activity_debate_profile_tablayout)
    TabLayout tabLayout;

    @BindView(R.id.activity_debate_profile_back_btn)
    ImageView backBtn;

    @BindView(R.id.activity_debate_profile_textview_title)
    TextView title;

    @BindView(R.id.activity_debate_profile_textview_start_time)
    TextView time;

    @BindView(R.id.activity_debate_profile_button_join_debate)
    RelativeLayout joinBtn;

    public static DebateProfileActivity instance=null;
    public String debateId=null;

    DebateProfilePresenter debateProfilePresenter;
    DebateProfileViewPagerAdapter debateProfileViewPagerAdapter;


    public static DebateProfileActivity newInstance(){
        return instance;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_debate_profile);
        ButterKnife.bind(this);

        instance=this;
        debateId=getIntent().getStringExtra("debateId");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initViews();

    }

    public void initViews(){
        debateProfileViewPagerAdapter=new DebateProfileViewPagerAdapter(getSupportFragmentManager(),debateId);
        viewPager.setAdapter(debateProfileViewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                overridePendingTransition(R.anim.enter_from_left,R.anim.exit_to_right);
            }
        });

    }


}
