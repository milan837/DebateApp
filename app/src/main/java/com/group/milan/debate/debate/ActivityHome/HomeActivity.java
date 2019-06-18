package com.group.milan.debate.debate.ActivityHome;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.group.milan.debate.debate.ActivityCategoryBaseDebateList.CategoryBaseDebateListActivity;
import com.group.milan.debate.debate.ActivityCreateDebate.CreateDebateActivity;
import com.group.milan.debate.debate.ActivityHome.model.HomeActivityResponse;
import com.group.milan.debate.debate.ActivityHome.model.News;
import com.group.milan.debate.debate.ActivityHome.model.Politice;
import com.group.milan.debate.debate.ActivityHome.model.Social;
import com.group.milan.debate.debate.ActivityHome.model.Sport;
import com.group.milan.debate.debate.ActivityHome.model.TopDebate;
import com.group.milan.debate.debate.ActivityHome.model.UserDetails;
import com.group.milan.debate.debate.ActivityMenu.DrawerMenuActivity;
import com.group.milan.debate.debate.R;
import com.group.milan.debate.debate.ActivitySearch.SearchActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeActivity extends AppCompatActivity implements HomeContract.Views{

    @BindView(R.id.activity_home_recyclerview_top_debate)
    RecyclerView recyclerViewTopDebate;

    @BindView(R.id.activity_home_recyclerview_social)
    RecyclerView recyclerViewSocial;

    @BindView(R.id.activity_home_recyclerview_sports)
    RecyclerView recyclerViewSport;

    @BindView(R.id.activity_home_recyclerview_politices)
    RecyclerView recyclerViewPolitices;

    @BindView(R.id.activity_home_recyclerview_news)
    RecyclerView recyclerViewNews;

    @BindView(R.id.activity_home_see_all_top_debate)
    AppCompatButton seeAllBtnTopDebate;

    @BindView(R.id.activity_home_see_all_social)
    AppCompatButton seeAllBtnSocial;

    @BindView(R.id.activity_home_see_all_news)
    AppCompatButton seeAllBtnNews;

    @BindView(R.id.activity_home_see_all_politices)
    AppCompatButton seeAllBtnPolitices;

    @BindView(R.id.activity_home_see_all_sports)
    AppCompatButton seeAllBtnSports;

    @BindView(R.id.activity_home_search_icon)
    ImageView searchBtn;

    @BindView(R.id.activity_home_menu_icon)
    ImageView menuIcon;

    public static HomeActivity homeActivity;

    //Response class;
    HomeActivityResponse homeActivityResponse;
    UserDetails userDetails;

    //list of data from api;
    List<TopDebate> topDebateList;
    List<Sport> sportList;
    List<Politice> politiceList;
    List<News> newsList;
    List<Social> socialsList;

    //adapter
    RecyclerViewAdapterTopDebateList adapterDebate;
    RecyclerViewAdapterSportDebateList adapterSport;
    RecyclerViewAdapterPoliticesDebateList adapterPolitice;
    RecyclerViewAdapterNewsDebateList adapterNews;
    RecyclerViewAdapterSocialDebateList adapterSocial;

    @BindView(R.id.activity_home_progressbar_top_debate)
    ProgressBar progressBarTopDebate;

    @BindView(R.id.activity_home_progressbar_news)
    ProgressBar progressBarNews;

    @BindView(R.id.activity_home_progressbar_politices)
    ProgressBar progressBarPolitices;

    @BindView(R.id.activity_home_progressbar_social)
    ProgressBar progressBarSocial;

    @BindView(R.id.activity_home_progressbar_sports)
    ProgressBar progressBarSports;

    String userId;

    HomePresenter presenter;

    public static HomeActivity newInstance(){
        return homeActivity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        homeActivity=this;

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //intitlzing the  list
        topDebateList=new ArrayList();
        sportList=new ArrayList();
        politiceList=new ArrayList();
        newsList=new ArrayList();
        socialsList=new ArrayList();

        //getting saved user id
        SharedPreferences sharedPreferences=getSharedPreferences("users",MODE_PRIVATE);
        userId=sharedPreferences.getString("user_id",null);

        presenter=new HomePresenter(this,getApplicationContext());
        presenter.sendDataToApi(userId);
        initViews();

    }

    private void initViews(){

        // adapter and recyclerview set up for the top debate debate list
        adapterDebate=new RecyclerViewAdapterTopDebateList(this,topDebateList);
        LinearLayoutManager topDebateLayoutManager=new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        recyclerViewTopDebate.setLayoutManager(topDebateLayoutManager);
        recyclerViewTopDebate.setItemAnimator(new DefaultItemAnimator());
        recyclerViewTopDebate.setAdapter(adapterDebate);

        // adapter and recyclerview set up for the top debate sports debate list
        adapterSport=new RecyclerViewAdapterSportDebateList(this,sportList);
        LinearLayoutManager socialLayoutManager=new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        recyclerViewSocial.setLayoutManager(socialLayoutManager);
        recyclerViewSocial.setItemAnimator(new DefaultItemAnimator());
        recyclerViewSocial.setAdapter(adapterSport);

        // adapter and recyclerview set up for the top politices debate list
        adapterPolitice=new RecyclerViewAdapterPoliticesDebateList(this,politiceList);
        LinearLayoutManager sportLayoutManager=new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        recyclerViewSport.setLayoutManager(sportLayoutManager);
        recyclerViewSport.setItemAnimator(new DefaultItemAnimator());
        recyclerViewSport.setAdapter(adapterPolitice);

        // adapter and recyclerview set up for the top news debate list
        adapterNews=new RecyclerViewAdapterNewsDebateList(this,newsList);
        LinearLayoutManager politicesLayoutManager=new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        recyclerViewPolitices.setLayoutManager(politicesLayoutManager);
        recyclerViewPolitices.setItemAnimator(new DefaultItemAnimator());
        recyclerViewPolitices.setAdapter(adapterNews);

        // adapter and recyclerview set up for the top social debate list
        adapterSocial=new RecyclerViewAdapterSocialDebateList(this,socialsList);
        LinearLayoutManager newsLayoutManager=new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        recyclerViewNews.setLayoutManager(newsLayoutManager);
        recyclerViewNews.setItemAnimator(new DefaultItemAnimator());
        recyclerViewNews.setAdapter(adapterSocial);


//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent=new Intent(HomeActivity.this,CreateDebateActivity.class);
//                startActivity(intent);
//                overridePendingTransition(R.anim.enter_from_right,R.anim.exit_to_left);
//            }
//        });

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(HomeActivity.this, SearchActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.enter_from_right,R.anim.exit_to_left);
            }
        });

        menuIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(HomeActivity.this, DrawerMenuActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.enter_from_left,R.anim.exit_to_right);
            }
        });


        //see all button handline
        seeAllBtnTopDebate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(HomeActivity.this, CategoryBaseDebateListActivity.class);
                intent.putExtra("keyword","top debate");
                startActivity(intent);
                overridePendingTransition(R.anim.enter_from_right,R.anim.exit_to_left);
            }
        });

        //see all button handline
        seeAllBtnNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(HomeActivity.this, CategoryBaseDebateListActivity.class);
                intent.putExtra("keyword","news");
                startActivity(intent);
                overridePendingTransition(R.anim.enter_from_right,R.anim.exit_to_left);
            }
        });

        //see all button handline
        seeAllBtnPolitices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(HomeActivity.this, CategoryBaseDebateListActivity.class);
                intent.putExtra("keyword","politices");
                startActivity(intent);
                overridePendingTransition(R.anim.enter_from_right,R.anim.exit_to_left);
            }
        });

        //see all button handline
        seeAllBtnSocial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(HomeActivity.this, CategoryBaseDebateListActivity.class);
                intent.putExtra("keyword","social");
                startActivity(intent);
                overridePendingTransition(R.anim.enter_from_right,R.anim.exit_to_left);
            }
        });

        //see all button handline
        seeAllBtnSports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(HomeActivity.this, CategoryBaseDebateListActivity.class);
                intent.putExtra("keyword","sports");
                startActivity(intent);
                overridePendingTransition(R.anim.enter_from_right,R.anim.exit_to_left);
            }
        });
    }

    @Override
    public void displayApiResponse(HomeActivityResponse homeActivityResponse) {
        this.homeActivityResponse=homeActivityResponse;
        this.userDetails=homeActivityResponse.getResponse().getUserDetails();

        //saving data to shared preferences;
        SharedPreferences sharedPreferences=getSharedPreferences("users",MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString("username",userDetails.getUsername());
        editor.putString("profile_picture",userDetails.getProfilePicture());
        editor.putString("levels",userDetails.getLevel());
        editor.putString("badges",userDetails.getBadges());
        editor.putString("email",userDetails.getEmail());
        editor.putString("total_debates",userDetails.getTotalDebates());
        editor.commit();

        progressBarNews.setVisibility(View.GONE);
        progressBarPolitices.setVisibility(View.GONE);
        progressBarSocial.setVisibility(View.GONE);
        progressBarSports.setVisibility(View.GONE);
        progressBarTopDebate.setVisibility(View.GONE);

        // Top debate response data
        for(int i=0; i<homeActivityResponse.getResponse().getTopDebates().size();i++){
            topDebateList.add(homeActivityResponse.getResponse().getTopDebates().get(i));
            adapterDebate.notifyDataSetChanged();
        }

        // social response data
        for(int i=0; i<homeActivityResponse.getResponse().getSocial().size();i++){
            socialsList.add(homeActivityResponse.getResponse().getSocial().get(i));
            adapterSocial.notifyDataSetChanged();
        }

        // news response data
        for(int i=0; i<homeActivityResponse.getResponse().getNews().size();i++){
            newsList.add(homeActivityResponse.getResponse().getNews().get(i));
            adapterNews.notifyDataSetChanged();
        }

        // sport response data
        for(int i=0; i<homeActivityResponse.getResponse().getSports().size();i++){
            sportList.add(homeActivityResponse.getResponse().getSports().get(i));
            adapterSport.notifyDataSetChanged();
        }

        // politices response data
        for(int i=0; i<homeActivityResponse.getResponse().getPolitices().size();i++){
            politiceList.add(homeActivityResponse.getResponse().getPolitices().get(i));
            adapterPolitice.notifyDataSetChanged();
        }
    }
}
