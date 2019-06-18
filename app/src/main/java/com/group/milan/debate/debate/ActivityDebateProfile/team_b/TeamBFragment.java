package com.group.milan.debate.debate.ActivityDebateProfile.team_b;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.JsonObject;
import com.group.milan.debate.debate.ActivityDebateProfile.team_b.model.Response;
import com.group.milan.debate.debate.ActivityDebateProfile.team_b.model.TeamUser;
import com.group.milan.debate.debate.ActivityDebateProfile.team_b.model.TeamUserPojo;
import com.group.milan.debate.debate.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TeamBFragment extends Fragment implements TeamBContact.Views{
    @BindView(R.id.fragment_debate_profile_recyclerview_team_b)
    RecyclerView recyclerView;

    @BindView(R.id.fragment_debate_profile_team_b_progressbar)
    ProgressBar progressBar;

    List<TeamUser> list;
    TeamBPresenter presenter;

    int getTotalcount,lastVisibleItem;
    boolean loadMoreStatus=true;

    RecyclerViewAdapterTeamB adapterTeamB;

    public static TeamBFragment newInstance() {
        return new TeamBFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_debate_profile_team_b, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        list=new ArrayList<>();

        this.presenter=new TeamBPresenter(this,getActivity().getApplicationContext());
        initViews();

    }

    public void sendDataToApi(String teamId,String paginationId){
        progressBar.setVisibility(View.VISIBLE);
        JsonObject jsonObject=new JsonObject();
        jsonObject.addProperty("team_id",teamId);
        jsonObject.addProperty("pagination_id",paginationId);
        presenter.sendDataToApi(jsonObject);
    }

    public void initViews(){
        sendDataToApi("1","0");

        adapterTeamB = new RecyclerViewAdapterTeamB(getActivity(), list);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapterTeamB);

        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                getTotalcount = layoutManager.getItemCount();
                lastVisibleItem = layoutManager.findLastVisibleItemPosition();
                if (getTotalcount - 1 == lastVisibleItem) {
                    TeamUser teamUser= (TeamUser) list.get(recyclerView.getAdapter().getItemCount() - 1);
                    if (!loadMoreStatus) {
                        Log.i("milan_last",teamUser.getUserId());
                        //hiting the api from view
                        sendDataToApi("1",teamUser.getUserId());
                        loadMoreStatus = true;
                    }
                } else {
                    loadMoreStatus = false;
                }
            }
        });

    }

    @Override
    public void displayResponseData(TeamUserPojo teamUserPojo) {
        progressBar.setVisibility(View.GONE);
        for(int i=0;i<teamUserPojo.getResponse().getTeamUser().size();i++){
            list.add(teamUserPojo.getResponse().getTeamUser().get(i));
            adapterTeamB.notifyDataSetChanged();
        }
    }
}
