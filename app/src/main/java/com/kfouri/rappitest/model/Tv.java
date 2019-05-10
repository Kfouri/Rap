package com.kfouri.rappitest.model;

import java.util.ArrayList;

public class Tv extends Video{

    private String original_name;
    private ArrayList<Integer> genre_ids = new ArrayList();
    private String name;
    private ArrayList<String> origin_country = new ArrayList();
    private String first_air_date;
    private String overview;

    public String getOriginal_name() {
        return original_name;
    }

    public void setOriginal_name(String original_name) {
        this.original_name = original_name;
    }

    public ArrayList<Integer> getGenre_ids() {
        return genre_ids;
    }

    public void setGenre_ids(ArrayList<Integer> genre_ids) {
        this.genre_ids = genre_ids;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<String> getOrigin_country() {
        return origin_country;
    }

    public void setOrigin_country(ArrayList<String> origin_country) {
        this.origin_country = origin_country;
    }

    public String getFirst_air_date() {
        return first_air_date;
    }

    public void setFirst_air_date(String first_air_date) {
        this.first_air_date = first_air_date;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

}