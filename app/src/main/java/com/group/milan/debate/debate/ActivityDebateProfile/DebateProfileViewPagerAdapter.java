package com.group.milan.debate.debate.ActivityDebateProfile;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.group.milan.debate.debate.ActivityDebateProfile.team_a.TeamAFragment;
import com.group.milan.debate.debate.ActivityDebateProfile.team_b.TeamBFragment;

public class DebateProfileViewPagerAdapter extends FragmentPagerAdapter {
    String debateId;

    public DebateProfileViewPagerAdapter(FragmentManager fm,String debateId) {
        super(fm);
        this.debateId=debateId;
    }

    @Override
    public Fragment getItem(int i) {
        Bundle bundle=new Bundle();
        bundle.putString("debate_id",debateId);
        if(i == 0){
            TeamAFragment teamAFragment=new TeamAFragment();
            teamAFragment.setArguments(bundle);
            return teamAFragment;
        }else if(i == 1){
            TeamBFragment teamBFragment=new TeamBFragment();
            teamBFragment.setArguments(bundle);
            return teamBFragment;
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
            return "Team A";
        }else{
            return "Team B";
        }
    }
}
