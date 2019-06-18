package com.group.milan.debate.debate.ActivityUserProfile;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.group.milan.debate.debate.ActivityUserProfile.debate.DebateFragment;
import com.group.milan.debate.debate.ActivityUserProfile.debate.model.Debate;
import com.group.milan.debate.debate.ActivityUserProfile.friend.FriendFragment;
import com.group.milan.debate.debate.ActivityUserProfile.my_squard.MySquardFragment;
import com.group.milan.debate.debate.ActivityUserProfile.my_squard.MySquardPresenter;

public class UserProfileAdapterViewPager extends FragmentPagerAdapter {
    String userId;
    public UserProfileAdapterViewPager(FragmentManager fm,String userId) {
        super(fm);
        this.userId=userId;
    }

    @Override
    public Fragment getItem(int i) {
        Bundle bundle=new Bundle();
        bundle.putString("user_id",userId);
        if(i==0){
            DebateFragment debateFragment=new DebateFragment();
            debateFragment.setArguments(bundle);
            return debateFragment;
//        }else if(i==1){
//            FriendFragment friendFragment=new FriendFragment();
//            friendFragment.setArguments(bundle);
//            return friendFragment;
        }else if(i==1){
            MySquardFragment mySquardFragment=new MySquardFragment();
            mySquardFragment.setArguments(bundle);
            return mySquardFragment;
        }

        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        if(position == 0){
            return "Debates";
//        }else if(position == 1){
//            return "Friends";
        }else if (position == 1){
            return "My Squard";
        }
        return null;
    }
}
