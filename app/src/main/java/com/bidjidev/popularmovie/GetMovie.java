package com.bidjidev.popularmovie;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by You on 6/29/17.
 */

public class GetMovie implements Parcelable{
    int    id;
    String title;
    String originalTitle;
    String overview;
    String popularity;
    String releaseDate;
    String posterPath;
    String backdropPath;
    String originalLanguage;
    String voteAverage;
    String voteCount;
    String tagline;
    String status;
    String runtime;
    String revenue;


    public GetMovie() {
    }
    private GetMovie(Parcel parcel){
        this.id = parcel.readInt();
        this.title = parcel.readString();
        this.originalTitle = parcel.readString();
        this.backdropPath = parcel.readString();
        this.posterPath = parcel.readString();
        this.overview = parcel.readString();
        this.voteAverage = parcel.readString();
        this.releaseDate = parcel.readString();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getoriginalTitle() {
        return originalTitle;
    }

    public void setoriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getPopularity() {
        return popularity;
    }

    public void setPopularity(String popularity) {
        this.popularity = popularity;
    }

    public String getreleaseDate() {
        return releaseDate;
    }

    public void setreleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getposterPath() {
        return posterPath;
    }

    public void setposterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getbackdropPath() {
        return backdropPath;
    }

    public void setbackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public String getOriginal_language() {
        return originalLanguage;
    }

    public void setOriginal_language(String original_language) {
        this.originalLanguage = original_language;
    }

    public String getvoteAverage() {
        return voteAverage;
    }

    public void setvoteAverage(String voteAverage) {
        this.voteAverage = voteAverage;
    }

    public String getVote_count() {
        return voteCount;
    }

    public void setVote_count(String vote_count) {
        this.voteCount = vote_count;
    }

    public String getTagline() {
        return tagline;
    }

    public void setTagline(String tagline) {
        this.tagline = tagline;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRuntime() {
        return runtime;
    }

    public void setRuntime(String runtime) {
        this.runtime = runtime;
    }

    public String getRevenue() {
        return revenue;
    }

    public void setRevenue(String revenue) {
        this.revenue = revenue;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeString(this.title);
        dest.writeString(this.originalTitle);
        dest.writeString(this.backdropPath);
        dest.writeString(this.posterPath);
        dest.writeString(this.overview);
        dest.writeString(this.voteAverage);
        dest.writeString(this.releaseDate);
    }

    public static final Creator<GetMovie> CREATOR = new Creator<GetMovie>() {
        @Override
        public GetMovie createFromParcel(Parcel parcel) {
            return new GetMovie(parcel);
        }

        @Override
        public GetMovie[] newArray(int i) {
            return new GetMovie[i];
        }

    };

}