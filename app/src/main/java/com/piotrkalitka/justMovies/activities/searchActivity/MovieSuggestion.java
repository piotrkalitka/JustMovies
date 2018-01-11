package com.piotrkalitka.justMovies.activities.searchActivity;

import android.os.Parcel;
import android.os.Parcelable;

import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;

class MovieSuggestion implements SearchSuggestion {

    private String id;
    private String name;

    MovieSuggestion(String id, String name){
        this.name = name;
        this.id = id;
    }

    @Override
    public String getBody() {
        return name;
    }

    public String getId() {
        return id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(name);
    }

    private MovieSuggestion(Parcel in) {
        this.name = in.readString();
        this.id = in.readString();
    }

    public static final Parcelable.Creator<MovieSuggestion> CREATOR = new Parcelable.Creator<MovieSuggestion>(){

        @Override
        public MovieSuggestion createFromParcel(Parcel parcel) {
            return new MovieSuggestion(parcel);
        }

        @Override
        public MovieSuggestion[] newArray(int i) {
            return new MovieSuggestion[i];
        }
    };
}
