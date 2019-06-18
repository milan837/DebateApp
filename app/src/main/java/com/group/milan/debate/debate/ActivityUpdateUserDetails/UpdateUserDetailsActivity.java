package com.group.milan.debate.debate.ActivityUpdateUserDetails;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;

import com.group.milan.debate.debate.ActivityUpdateUserDetails.ProfilePictureUpdate.UpdateProfilePictureFragment;
import com.group.milan.debate.debate.ActivityUpdateUserDetails.UsernameUpdate.UpdateUsernameFragment;
import com.group.milan.debate.debate.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UpdateUserDetailsActivity extends AppCompatActivity {

    @BindView(R.id.activity_update_uname_pp_fragment_frame_layout)
    FrameLayout frameLayout;
    String status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_user_details);
        ButterKnife.bind(this);

        status=getIntent().getStringExtra("status");

        if(status.equals("profile_picture")){
            UpdateProfilePictureFragment updateProfilePictureFragment=new UpdateProfilePictureFragment();
            FragmentManager fragmentManager=getSupportFragmentManager();
            FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.activity_update_uname_pp_fragment_frame_layout,updateProfilePictureFragment,"update_profile_picture");
            fragmentTransaction.setCustomAnimations(R.anim.enter_from_right,R.anim.exit_to_left);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }else if(status.equals("username")){
            UpdateUsernameFragment updateUsernameFragment=new UpdateUsernameFragment();
            FragmentManager fragmentManager=getSupportFragmentManager();
            FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.activity_update_uname_pp_fragment_frame_layout,updateUsernameFragment,"update_username");
            fragmentTransaction.setCustomAnimations(R.anim.enter_from_right,R.anim.exit_to_left);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }


    }

}
