package com.group.milan.debate.debate.ActivityUserProfile.debate;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.gson.JsonObject;
import com.group.milan.debate.debate.ActivityChatBoxImageView.ChatBoxImageViewActivity;
import com.group.milan.debate.debate.ActivityUpdateUserDetails.UpdateUserDetailsActivity;
import com.group.milan.debate.debate.ActivityUserProfile.UserProfileActivity;
import com.group.milan.debate.debate.ActivityUserProfile.debate.model.Debate;
import com.group.milan.debate.debate.ActivityUserProfile.debate.model.UserDetails;
import com.group.milan.debate.debate.ActivityUserProfile.debate.model.UserProfilePojo;
import com.group.milan.debate.debate.R;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DebateFragment extends Fragment implements DebateContract.Views {

    @BindView(R.id.fragment_user_profile_debate_recyclerview)
    RecyclerView recyclerView;

    @BindView(R.id.fragment_debate_search_progressbar)
    ProgressBar progressBar;

    ImageView optionBtn;

    DebateContract.Presenter presenter;
    List<Debate> list;
    RecyclerViewUserProfileAdapterDebate adapterDebate;
    UserDetails userDetails;

    int getTotalcount,lastVisibleItem;
    boolean loadMoreStatus=true;

    TextView badges,level,winner,username;
    CircularImageView profilePic;
    public String userId;

    PopupMenu popupMenu;

    public static DebateFragment newInstance(){
        return new DebateFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_user_profile_debate,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this,view);
        list=new ArrayList();

        userId=getArguments().getString("user_id");

        presenter=new DebatePresenter(this,getActivity());
        initViews();
    }

    private void initViews(){
        badges=(TextView)getActivity().findViewById(R.id.activity_user_profile_badges);
        level=(TextView)getActivity().findViewById(R.id.activity_user_profile_level);
        winner=(TextView)getActivity().findViewById(R.id.activity_user_profile_winner);
        username=(TextView)getActivity().findViewById(R.id.activity_user_profile_username);
        profilePic=(CircularImageView)getActivity().findViewById(R.id.activity_user_profile_image);
        optionBtn=(ImageView)getActivity().findViewById(R.id.activity_debate_profile_menu_option);

        sendDataToApi(userId,"0");

        adapterDebate=new RecyclerViewUserProfileAdapterDebate(getActivity(),list);
        final LinearLayoutManager layoutManager=new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapterDebate);


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
                    Debate debate= (Debate) list.get(recyclerView.getAdapter().getItemCount() - 1);
                    if (!loadMoreStatus) {
                        Log.i("milan_last",debate.getId());
                        //hiting the api from view
                        sendDataToApi(userId,debate.getId());
                        loadMoreStatus = true;
                    }
                } else {
                    loadMoreStatus = false;
                }
            }
        });

        optionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popup();
            }
        });

        profilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(), ChatBoxImageViewActivity.class);
                intent.putExtra("status","server");
                intent.putExtra("image_url",userDetails.getProfilePicture());
                startActivity(intent);
            }
        });
    }

    //popup menu
    public void popup(){
        popupMenu=new PopupMenu(getActivity(),optionBtn);
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.user_profile_update_pp:
                        Intent intent=new Intent(getActivity(), UpdateUserDetailsActivity.class);
                        intent.putExtra("status","profile_picture");
                        startActivity(intent);
                        UserProfileActivity.newInstance().overridePendingTransition(R.anim.enter_from_right,R.anim.exit_to_left);
                        return true;
                    case R.id.user_profile_update_uname:
                        Intent intent1=new Intent(getActivity(), UpdateUserDetailsActivity.class);
                        intent1.putExtra("status","username");
                        startActivity(intent1);
                        UserProfileActivity.newInstance().overridePendingTransition(R.anim.enter_from_right,R.anim.exit_to_left);
                        return true;

                    default:
                        return false;
                }
            }
        });

        MenuInflater menuInflater=popupMenu.getMenuInflater();
        menuInflater.inflate(R.menu.user_profile_menu,popupMenu.getMenu());
        popupMenu.show();
    }

    public void sendDataToApi(String userId,String paginationId){
        progressBar.setVisibility(View.VISIBLE);
        JsonObject jsonObject=new JsonObject();
        jsonObject.addProperty("user_id",userId);
        jsonObject.addProperty("pagination_id",paginationId);
        presenter.sendDataToApi(jsonObject);
    }

    @Override
    public void displayResponse(UserProfilePojo userProfilePojo) {
        progressBar.setVisibility(View.GONE);
        this.userDetails=userProfilePojo.getResponse().getUserDetails();
        String myUsername=userDetails.getUsername().substring(0,1).toUpperCase()+userDetails.getUsername().substring(1).toLowerCase();

        badges.setText(userDetails.getBadges());
        level.setText(userDetails.getLevel());
        winner.setText(userDetails.getWinner());
        username.setText(myUsername);

        Glide.with(getActivity()).load(userDetails.getProfilePicture())
                .thumbnail(0.5f)
                .crossFade()
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(profilePic);

        for(int i=0;i<userProfilePojo.getResponse().getDebates().size();i++){
            list.add(userProfilePojo.getResponse().getDebates().get(i));
            adapterDebate.notifyDataSetChanged();
        }

    }
}
