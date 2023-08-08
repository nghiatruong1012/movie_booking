package com.example.moviebooking;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Show {

@SerializedName("id")
@Expose
private String id;
@SerializedName("movie_id")
@Expose
private String movieId;
@SerializedName("cinema_id")
@Expose
private String cinemaId;
@SerializedName("date")
@Expose
private String date;
@SerializedName("start")
@Expose
private String start;
@SerializedName("end")
@Expose
private String end;
@SerializedName("price")
@Expose
private String price;

public String getId() {
return id;
}

public void setId(String id) {
this.id = id;
}

public String getMovieId() {
return movieId;
}

public void setMovieId(String movieId) {
this.movieId = movieId;
}

public String getCinemaId() {
return cinemaId;
}

public void setCinemaId(String cinemaId) {
this.cinemaId = cinemaId;
}

public String getDate() {
return date;
}

public void setDate(String date) {
this.date = date;
}

public String getStart() {
return start;
}

public void setStart(String start) {
this.start = start;
}

public String getEnd() {
return end;
}

public void setEnd(String end) {
this.end = end;
}

public String getPrice() {
        return price;
    }

public void setPrice(String price) {
        this.price = price;
    }

}