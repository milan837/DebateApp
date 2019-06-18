package com.group.milan.debate.debate.ActivitySearch;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.KeyListener;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.google.gson.JsonObject;
import com.group.milan.debate.debate.ActivitySearch.model.Response;
import com.group.milan.debate.debate.ActivitySearch.model.SearchResponsePojo;
import com.group.milan.debate.debate.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchActivity extends AppCompatActivity implements SearchContract.Views {

    @BindView(R.id.activity_search_recyclerview_users)
    RecyclerView recyclerview;

    @BindView(R.id.activity_search_back_button)
    ImageView backBtn;

    @BindView(R.id.activity_search_edittext_search_box)
    EditText searchBox;

    @BindView(R.id.activity_search_progressbar)
    ProgressBar progressBar;

    public static SearchActivity instance=null;
    List<Response> list;
    String keyword=null;

    RecyclerViewAdapterUserDetails adapterUserDetails;
    SearchPresenter presenter;

    int getTotalcount,lastVisibleItem;
    boolean loadMoreStatus=true;

    public static SearchActivity newInstance(){
        return instance;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
        instance=this;

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        list=new ArrayList();
        this.presenter=new SearchPresenter(this,getApplicationContext());
        initViews();

    }

    public void initViews(){
        adapterUserDetails=new RecyclerViewAdapterUserDetails(this,list);
        final LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        recyclerview.setItemAnimator(new DefaultItemAnimator());
        recyclerview.setAdapter(adapterUserDetails);
        recyclerview.setLayoutManager(layoutManager);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                overridePendingTransition(R.anim.enter_from_left,R.anim.exit_to_right);
            }
        });

        searchBox.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                String getKword=searchBox.getText().toString().trim();
                if((keyEvent.getAction() == KeyEvent.ACTION_DOWN) && (i == KeyEvent.KEYCODE_ENTER)){
                    keyword=getKword;
                    list.clear();
                    adapterUserDetails.notifyDataSetChanged();
                    sendDataToApi(keyword,"0");
                    return true;
                }
                return false;
            }
        });

        recyclerview.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                getTotalcount = layoutManager.getItemCount();
                lastVisibleItem = layoutManager.findLastVisibleItemPosition();
                if(list.size()>0) {
                    if (getTotalcount - 1 == lastVisibleItem) {
                        Response response = (Response) list.get(recyclerView.getAdapter().getItemCount() - 1);
                        if (!loadMoreStatus) {
                            Log.i("milan_last", response.getUserId());
                            Log.i("milan_last", keyword);
                            //hiting the api from view
                            sendDataToApi(keyword, response.getUserId());
                            loadMoreStatus = true;
                        }
                    } else {
                        loadMoreStatus = false;
                    }
                }
            }
        });
    }

    public void sendDataToApi(String keyword,String paginationId){
        progressBar.setVisibility(View.VISIBLE);
        JsonObject jsonObject=new JsonObject();
        jsonObject.addProperty("keyword",keyword);
        jsonObject.addProperty("pagination_id",paginationId);
        presenter.sendDataToApi(jsonObject);
    }

    @Override
    public void displayResponseData(SearchResponsePojo searchResponsePojo) {
        progressBar.setVisibility(View.GONE);
        for(int i=0;i<searchResponsePojo.getResponse().size();i++){
            list.add(searchResponsePojo.getResponse().get(i));
            adapterUserDetails.notifyDataSetChanged();
        }
    }
}
