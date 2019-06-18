package com.group.milan.debate.debate.ActivityMenu;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.group.milan.debate.debate.ActivityAllCategory.AllCategoryActivity;
import com.group.milan.debate.debate.ActivityAllWebView.WebViewActivity;
import com.group.milan.debate.debate.ActivityChangePassword.ChangePasswordActivity;
import com.group.milan.debate.debate.ActivityChatBoxImageView.ChatBoxImageViewActivity;
import com.group.milan.debate.debate.ActivityUserProfile.UserProfileActivity;
import com.group.milan.debate.debate.R;
import com.mikhaellopez.circularimageview.CircularImageView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DrawerMenuActivity extends AppCompatActivity {
    @BindView(R.id.activity_menu_back_button)
    ImageView backBtn;

    @BindView(R.id.activity_menu_profile_picture)
    CircularImageView profilePic;

    @BindView(R.id.activity_menu_username)
    TextView username;

    @BindView(R.id.activity_menu_level)
    TextView levelsTv;

    @BindView(R.id.activity_menu_item_profile)
    TextView profile;

    @BindView(R.id.activity_menu_item_all_category)
    TextView allCategory;

    @BindView(R.id.activity_menu_item_change_password)
    TextView changePassword;

    @BindView(R.id.activity_menu_item_developer_details)
    TextView developerDetails;

    @BindView(R.id.activity_menu_item_feedback)
    TextView feedback;

    @BindView(R.id.activity_menu_item_message_us)
    TextView messageUs;

    @BindView(R.id.activity_menu_item_logout)
    TextView logout;


    String userId,uName,profilePicture,levels,badges,totalDebate;

    public static DrawerMenuActivity instance=null;

    public static DrawerMenuActivity newInstance(){
        return instance;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer_menu);
        ButterKnife.bind(this);
        instance=this;

        SharedPreferences sharedPreferences=getSharedPreferences("users",MODE_PRIVATE);
        userId=sharedPreferences.getString("user_id",null);
        uName=sharedPreferences.getString("username",null);
        profilePicture=sharedPreferences.getString("profile_picture",null);
        levels=sharedPreferences.getString("levels",null);
        badges=sharedPreferences.getString("badges",null);
        totalDebate=sharedPreferences.getString("total_debates",null);

        //setting text
        username.setText(uName);

        Glide.with(this).load(profilePicture)
                .thumbnail(0.5f)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(profilePic);

        levelsTv.setText(levels);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        profilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(DrawerMenuActivity.this, ChatBoxImageViewActivity.class);
                intent.putExtra("status","server");
                intent.putExtra("image_url",profilePicture);
                startActivity(intent);
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                overridePendingTransition(R.anim.enter_from_right,R.anim.exit_to_left);
            }
        });

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                Intent intent=new Intent(DrawerMenuActivity.this,UserProfileActivity.class);
                intent.putExtra("user_id",userId);
                startActivity(intent);
                overridePendingTransition(R.anim.enter_from_right,R.anim.exit_to_left);
            }
        });

        allCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                Intent intent=new Intent(DrawerMenuActivity.this, AllCategoryActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.enter_from_right,R.anim.exit_to_left);

            }
        });

        feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                Intent intent=new Intent(DrawerMenuActivity.this,ChangePasswordActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.enter_from_right,R.anim.exit_to_left);
            }
        });

        developerDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                Intent intent=new Intent(DrawerMenuActivity.this, WebViewActivity.class);
                intent.putExtra("status","bio");
                startActivity(intent);
                overridePendingTransition(R.anim.enter_from_right,R.anim.exit_to_left);
            }
        });

        messageUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                Intent intent=new Intent(DrawerMenuActivity.this, WebViewActivity.class);
                intent.putExtra("status","message");
                startActivity(intent);
                overridePendingTransition(R.anim.enter_from_right,R.anim.exit_to_left);
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences=getSharedPreferences("users",MODE_PRIVATE);
                SharedPreferences.Editor editor=sharedPreferences.edit();
                editor.clear();
            }
        });
    }

}
