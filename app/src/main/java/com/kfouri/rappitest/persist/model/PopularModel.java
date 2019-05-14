package com.kfouri.rappitest.persist.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "popular")
public class PopularModel {

    @PrimaryKey
    @NonNull
    private Integer id;

    @NonNull
    private String name;

    @NonNull
    private String poster_path;

    @NonNull
    private Integer popular_order;

    @NonNull
    private Boolean isMovie;


    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    private byte[] image;

    @NonNull
    public Integer getId() {
        return id;
    }

    public void setId(@NonNull Integer id) {
        this.id = id;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    @NonNull
    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(@NonNull String poster_path) {
        this.poster_path = poster_path;
    }

    @NonNull
    public Integer getPopular_order() {
        return popular_order;
    }

    public void setPopular_order(@NonNull Integer order) {
        this.popular_order = order;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    @NonNull
    public Boolean getMovie() {
        return isMovie;
    }

    public void setMovie(@NonNull Boolean movie) {
        isMovie = movie;
    }
}
