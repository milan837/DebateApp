package com.group.milan.debate.debate.ActivityUserProfile.my_squard;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.JsonObject;
import com.group.milan.debate.debate.ActivityChatBox.pojo.ExitChatResponse;
import com.group.milan.debate.debate.ActivityUserProfile.my_squard.model.MySquardPojo;
import com.group.milan.debate.debate.ActivityUserProfile.my_squard.model.Response;
import com.group.milan.debate.debate.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MySquardFragment extends Fragment implements MySquardContract.Views {

    @BindView(R.id.fragment_user_profile_my_squard_recyclerview)
    RecyclerView recyclerView;

    @BindView(R.id.fragment_user_profile_squard_search_progressbar)
    ProgressBar progressBar;

    MySquardPresenter presenter;

    List<Response> list;
    MySquardRecyclerViewAdapter adapter;
    String userId;

    int getTotalcount,lastVisibleItem;
    boolean loadMoreStatus=true;

    public static MySquardFragment newInstance(){
        return new MySquardFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_user_profile_my_squard,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this,view);
        list=new ArrayList();
        userId=getArguments().getString("user_id");
        this.presenter=new MySquardPresenter(this,getActivity());
        initViews();
    }

    public void initViews(){
        sendDataToApi(userId,"0");

        adapter=new MySquardRecyclerViewAdapter(getActivity(),list);
        final GridLayoutManager layoutManager=new GridLayoutManager(getActivity(),2);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if(list.size() >= 1) {
                    getTotalcount = layoutManager.getItemCount();
                    lastVisibleItem = layoutManager.findLastVisibleItemPosition();
                    if (getTotalcount - 1 == lastVisibleItem) {
                        Response response = (Response) list.get(recyclerView.getAdapter().getItemCount() - 1);
                        if (!loadMoreStatus) {
                            Log.i("milan_last", response.getUserId());
                            //hiting the api from view
                            sendDataToApi(userId, response.getUserId());
                            loadMoreStatus = true;
                        }
                    } else {
                        loadMoreStatus = false;
                    }
                }
            }
        });

    }

    public void sendDataToApi(String userId,String paginationId){
        progressBar.setVisibility(View.VISIBLE);
        JsonObject jsonObject=new JsonObject();
        jsonObject.addProperty("user_id",userId);
        jsonObject.addProperty("pagination_id",paginationId);
        presenter.sendDataToApi(jsonObject);
    }

    @Override
    public void displayResponseData(MySquardPojo mySquardPojo) {
        progressBar.setVisibility(View.GONE);
        for(int i=0;i<mySquardPojo.getResponse().size();i++){
            list.add(mySquardPojo.getResponse().get(i));
            adapter.notifyDataSetChanged();
        }
    }
}
