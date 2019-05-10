package com.kfouri.rappitest.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Movie extends Video{

    @SerializedName("video")
    private Boolean video;
    @SerializedName("title")
    private String title;
    @SerializedName("release_date")
    private String release_date;
    @SerializedName("original_title")
    private String original_title;
    @SerializedName("genre_ids")
    private ArrayList genre_ids = new ArrayList();
    @SerializedName("adult")
    private Boolean adult;
    @SerializedName("overview")
    private String overview;

    public String getTitle(){
        return title;
    }

    public Boolean getVideo() {
        return video;
    }

    public void setVideo(Boolean video) {
        this.video = video;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public void setOriginal_title(String original_title) {
        this.original_title = original_title;
    }

    public ArrayList<Integer> getGenre_ids() {
        return genre_ids;
    }

    public void setGenre_ids(ArrayList<Integer> genre_ids) {
        this.genre_ids = genre_ids;
    }

    public Boolean getAdult() {
        return adult;
    }

    public void setAdult(Boolean adult) {
        this.adult = adult;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

}
