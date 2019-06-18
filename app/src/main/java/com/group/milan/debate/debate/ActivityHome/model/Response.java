package com.group.milan.debate.debate.ActivityHome.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Response {
    @SerializedName("user_details")
    @Expose
    private UserDetails userDetails;

    @SerializedName("top_debates")
    @Expose
    private List<TopDebate> topDebates = null;
    @SerializedName("social")
    @Expose
    private List<Social> social = null;
    @SerializedName("politices")
    @Expose
    private List<Politice> politices = null;
    @SerializedName("sports")
    @Expose
    private List<Sport> sports = null;
    @SerializedName("news")
    @Expose
    private List<News> news = null;

    public List<TopDebate> getTopDebates() {
        return topDebates;
    }

    public void setTopDebates(List<TopDebate> topDebates) {
        this.topDebates = topDebates;
    }

    public List<Social> getSocial() {
        return social;
    }

    public void setSocial(List<Social> social) {
        this.social = social;
    }

    public List<Politice> getPolitices() {
        return politices;
    }

    public void setPolitices(List<Politice> politices) {
        this.politices = politices;
    }

    public List<Sport> getSports() {
        return sports;
    }

    public void setSports(List<Sport> sports) {
        this.sports = sports;
    }

    public List<News> getNews() {
        return news;
    }

    public void setNews(List<News> news) {
        this.news = news;
    }

    public void setUserDetails(UserDetails userDetails) {
        this.userDetails = userDetails;
    }

    public UserDetails getUserDetails() {
        return userDetails;
    }
}