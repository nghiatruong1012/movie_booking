package com.example.moviebooking;


import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Movie implements Parcelable {

@SerializedName("id")
@Expose
private String id;
@SerializedName("name")
@Expose
private String name;
@SerializedName("description")
@Expose
private String description;
@SerializedName("poster")
@Expose
private String poster;
    @SerializedName("trailer")
    @Expose
    private String trailer;
    @SerializedName("release_date")
    @Expose
    private String releaseDate;
    @SerializedName("duration")
    @Expose
    private String duration;
    @SerializedName("rating")
    @Expose
    private String rating;
    @SerializedName("director")
    @Expose
    private String director;
    @SerializedName("actor")
    @Expose
    private String actor;
    @SerializedName("genre")
    @Expose
    private String genre;

    public Movie(String id, String name, String description, String poster, String trailer) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.poster = poster;
        this.trailer = trailer;
    }

    protected Movie(Parcel in) {
        id = in.readString();
        name = in.readString();
        description = in.readString();
        poster = in.readString();
        trailer = in.readString();
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    public String getId() {
return id;
}

public void setId(String id) {
this.id = id;
}

public String getName() {
return name;
}

public void setName(String name) {
this.name = name;
}

public String getDescription() {
return description;
}

public void setDescription(String description) {
this.description = description;
}

public String getPoster() {
return poster;
}

public void setPoster(String poster) {
this.poster = poster;
}

    public String getTrailer() {
        return trailer;
    }

    public void setTrailer(String trailer) {
        this.trailer = trailer;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String  releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getActor() {
        return actor;
    }

    public void setActor(String actor) {
        this.actor = actor;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }



    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(name);
        parcel.writeString(description);
        parcel.writeString(poster);
        parcel.writeString(trailer);
    }
}
