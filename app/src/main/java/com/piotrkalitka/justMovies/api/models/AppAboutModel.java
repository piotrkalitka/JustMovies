package com.piotrkalitka.justMovies.api.models;

import com.google.gson.annotations.SerializedName;

public class AppAboutModel {

    @SerializedName("label")
    private String label;
    @SerializedName("entry")
    private String entry;
    @SerializedName("onclick_url")
    private String onClickUrl;

    public String getLabel() {
        return label;
    }

    public String getEntry() {
        return entry;
    }

    public String getOnClickUrl(){
        return  onClickUrl;
    }

}