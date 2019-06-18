package com.group.milan.debate.debate.ActivityDebateProfile.team_a;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.group.milan.debate.debate.ActivityChatBox.ChatBoxActivity;
import com.group.milan.debate.debate.ActivityDebateProfile.model.AddToSquardPojo;
import com.group.milan.debate.debate.ActivityDebateProfile.team_a.model.TeamAUserPojo;
import com.group.milan.debate.debate.ActivityDebateProfile.team_a.model.TeamUser;
import com.group.milan.debate.debate.ActivityDebateProfile.team_a.model.joinBtn.JoinButtonResponse;
import com.group.milan.debate.debate.ActivityDebateProfile.team_a.model.selectTeam.SelectTeamResponse;
import com.group.milan.debate.debate.R;
import com.group.milan.debate.debate.Utils.UtilsClass;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TeamAFragment extends Fragment implements TeamAContract.Views{

    @BindView(R.id.fragment_debate_profile_recyclerview_team_a)
    RecyclerView recyclerView;

    @BindView(R.id.fragment_debate_profile_team_a_search_progressbar)
    ProgressBar progressBar;

    RecyclerViewAdapterTeamA adapterTeamA;

    TextView debateTitle,debateTime,teamAName,teamBName,teamAUserCount,teamBUserCount;
    RelativeLayout joinButton;
    PopupMenu popupMenu;

    TeamAPresenter teamAPresenter;
    List<TeamUser> list;

    //response pojo
    TeamAUserPojo teamAUserPojo;
    SelectTeamResponse selectTeamResponse,exitChatResponse;
    JoinButtonResponse joinButtonResponse;

    int getTotalcount,lastVisibleItem;
    boolean loadMoreStatus=false;
    String userId,debateId,debateTeamId;
    ProgressDialog progressDialog;
    int titleStatus=0;

    public static TeamAFragment newInstance(){
        return new TeamAFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_debate_profile_team_a,container,false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this,view);

        this.teamAPresenter=new TeamAPresenter(this,getActivity().getApplicationContext());

        SharedPreferences sharedPreferences=getActivity().getSharedPreferences("users", Context.MODE_PRIVATE);
        userId=sharedPreferences.getString("user_id",null);

        progressDialog=new ProgressDialog(getActivity());
        progressDialog.setMessage("Joining..");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);

        debateId=getArguments().getString("debate_id");

        initViews();
    }

    //SEND DATA API FOR API CALL
    public void sendDataToApi(String debateId,String paginationId){
        progressBar.setVisibility(View.VISIBLE);
        JsonObject jsonObject=new JsonObject();
        jsonObject.addProperty("debate_id",debateId);
        jsonObject.addProperty("pagination_id", paginationId);
        teamAPresenter.sendDataToApi(jsonObject);
    }

    //send data to join btn api
    public void joinBtnSendDataApi(){
        JsonObject jsonObject=new JsonObject();
        jsonObject.addProperty("debate_id",debateId);
        jsonObject.addProperty("user_id",userId);
        teamAPresenter.joinButtonCheckSendData(jsonObject);
    }

    //send data to join btn api
    public void selectTeamSendDataApi(){
        JsonObject jsonObject=new JsonObject();
        jsonObject.addProperty("debate_id",debateId);
        jsonObject.addProperty("user_id",userId);
        jsonObject.addProperty("debate_team_id",debateTeamId);

        Log.i("milan_l",userId);
        Log.i("milan_l",debateTeamId);
        Log.i("milan_l",debateId);
        teamAPresenter.selectTeamSendData(jsonObject);
    }

    public void initViews(){

        debateTitle=(TextView)getActivity().findViewById(R.id.activity_debate_profile_textview_title);
        debateTime=(TextView)getActivity().findViewById(R.id.activity_debate_profile_textview_start_time);
        teamAName=(TextView)getActivity().findViewById(R.id.activity_debate_profile_textview_team_a_total_member);
        teamBName=(TextView)getActivity().findViewById(R.id.activity_debate_profile_textview_team_b_name);
        teamAUserCount=(TextView)getActivity().findViewById(R.id.activity_debate_profile_textview_team_a_total_member);
        teamBUserCount=(TextView)getActivity().findViewById(R.id.activity_debate_profile_textview_team_b_total_member);
        joinButton=(RelativeLayout)getActivity().findViewById(R.id.activity_debate_profile_button_join_debate);

        joinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.show();
                joinBtnSendDataApi();
            }
        });

        list=new ArrayList<>();

        sendDataToApi(debateId,"0");

        adapterTeamA=new RecyclerViewAdapterTeamA(getActivity(),list);
        final LinearLayoutManager layoutManager=new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapterTeamA);

        //pagination cal for api list
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
                    if (!loadMoreStatus ) {
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

    //popup menu
    public void popup(){
        popupMenu=new PopupMenu(getActivity(),joinButton);
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.join_btn_team_a:
                        debateTeamId=teamAUserPojo.getResponse().getDebateInfo().getTeamAId();

                        if(debateTeamId != null){
                            selectTeamSendDataApi();
                            Log.i("milan_l",debateTeamId);
                        }else{
                            Toast.makeText(getActivity(), "null", Toast.LENGTH_SHORT).show();
                        }

                        return true;
                    case R.id.join_btn_team_b:
                        debateTeamId=teamAUserPojo.getResponse().getDebateInfo().getTeamBId();

                        if(debateTeamId != null){
                            selectTeamSendDataApi();
                            Log.i("milan_l",debateTeamId);
                        }else{
                            Toast.makeText(getActivity(), "null", Toast.LENGTH_SHORT).show();
                        }

                        return true;
                    default:
                        return false;
                }
            }
        });
        MenuInflater menuInflater=popupMenu.getMenuInflater();
        menuInflater.inflate(R.menu.join_btn_menu,popupMenu.getMenu());
        popupMenu.show();
    }

    @Override
    public void displayTeamAResponse(TeamAUserPojo teamAUserPojo) {
        progressBar.setVisibility(View.GONE);
        this.teamAUserPojo=teamAUserPojo;

        //only once load debate data
        if(titleStatus==0){
            debateTime.setText(UtilsClass.getConvertTime(Long.valueOf(teamAUserPojo.getResponse().getDebateInfo().getTime())));
            debateTitle.setText(teamAUserPojo.getResponse().getDebateInfo().getTitle());

            teamAUserCount.setText("Users "+Html.fromHtml("<b>"+teamAUserPojo.getResponse().getDebateInfo().getTeamATotalUsers()+"</b>"));
            teamBUserCount.setText("Users "+ Html.fromHtml("<b>"+teamAUserPojo.getResponse().getDebateInfo().getTeamBTotalUsers()+"</b>"));

            teamAName.setText(teamAUserPojo.getResponse().getDebateInfo().getTeamAName());
            teamBName.setText(teamAUserPojo.getResponse().getDebateInfo().getTeamBName());
            debateId=teamAUserPojo.getResponse().getDebateInfo().getId();

            titleStatus=1;
        }

        for(int i=0;i<teamAUserPojo.getResponse().getTeamUsers().size();i++){
            list.add(teamAUserPojo.getResponse().getTeamUsers().get(i));
            adapterTeamA.notifyDataSetChanged();
        }
        loadMoreStatus = false;
    }

    @Override
    public void displayJoinBtnResponse(JoinButtonResponse joinButtonResponse) {
        this.joinButtonResponse=joinButtonResponse;
        if(joinButtonResponse.getResponse().getStatus().equals("1")){

            progressDialog.hide();
            progressDialog.dismiss();
            Intent intent=new Intent(getActivity(), ChatBoxActivity.class);
            intent.putExtra("debate_id",debateId);
            intent.putExtra("user_id",userId);
            intent.putExtra("debate_team_id",joinButtonResponse.getResponse().getDebateTeamId());

            startActivity(intent);
            getActivity().overridePendingTransition(R.anim.enter_from_right,R.anim.exit_to_left);

        }else{
            progressDialog.hide();
            popup();
        }
    }

    @Override
    public void displaySelectTeamResponse(SelectTeamResponse selectTeamResponse) {
        this.selectTeamResponse=selectTeamResponse;
        if(selectTeamResponse.getResponse().getStatus().equals("ok")){
            progressDialog.hide();
            progressDialog.dismiss();

            Intent intent=new Intent(getActivity(), ChatBoxActivity.class);
            intent.putExtra("debate_id",debateId);
            intent.putExtra("user_id",userId);
            intent.putExtra("debate_team_id",debateTeamId);

            startActivity(intent);
            getActivity().overridePendingTransition(R.anim.enter_from_right,R.anim.exit_to_left);

        }else{
            progressDialog.hide();
            progressDialog.dismiss();
            Toast.makeText(getActivity(),selectTeamResponse.getResponse().getStatus(),Toast.LENGTH_LONG).show();
        }
    }

}
