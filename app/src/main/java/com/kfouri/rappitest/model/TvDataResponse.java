package com.kfouri.rappitest.model;

import java.util.ArrayList;

public class TvDataResponse {
    private String first_air_date;
    private String last_air_date;
    private String homepage;
    private ArrayList<String> languages;
    private Integer id;
    private String name;
    private Integer number_of_episodes;
    private Integer number_of_seasons;
    private String original_name;
    private String overview;
    private Double popularity;
    private String poster_path;
    private Float vote_average;
    private Integer vote_count;
    private ArrayList<Season> seasons;
    private VideoPlay videos;

    public VideoPlay getVideos() {
        return videos;
    }

    public void setVideos(VideoPlay videos) {
        this.videos = videos;
    }

    public String getFirst_air_date() {
        return first_air_date;
    }

    public String getLast_air_date() {
        return last_air_date;
    }

    public String getHomepage() {
        return homepage;
    }

    public ArrayList<String> getLanguages() {
        return languages;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getNumber_of_episodes() {
        return number_of_episodes;
    }

    public Integer getNumber_of_seasons() {
        return number_of_seasons;
    }

    public String getOriginal_name() {
        return original_name;
    }

    public String getOverview() {
        return overview;
    }

    public Double getPopularity() {
        return popularity;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public Float getVote_average() {
        return vote_average;
    }

    public Integer getVote_count() {
        return vote_count;
    }

    public ArrayList<Season> getSeasons() {
        return seasons;
    }
}
