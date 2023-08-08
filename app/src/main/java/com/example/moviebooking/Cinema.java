package com.example.moviebooking;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;


public class Cinema {

@SerializedName("id")
@Expose
private String id;
@SerializedName("cinema_name")
@Expose
private String cinemaName;
@SerializedName("address")
@Expose
private String address;

public String getId() {
return id;
}

public void setId(String id) {
this.id = id;
}

public String getCinemaName() {
return cinemaName;
}

public void setCinemaName(String cinemaName) {
this.cinemaName = cinemaName;
}

public String getAddress() {
return address;
}

public void setAddress(String address) {
this.address = address;
}

}