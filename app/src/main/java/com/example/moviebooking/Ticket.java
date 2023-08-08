package com.example.moviebooking;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Ticket {

@SerializedName("id")
@Expose
private String id;
@SerializedName("user_id")
@Expose
private String userId;
@SerializedName("show_id")
@Expose
private String showId;
@SerializedName("number_of_seat")
@Expose
private String numberOfSeat;
@SerializedName("seat")
@Expose
private String seat;

public String getId() {
return id;
}

public void setId(String id) {
this.id = id;
}

public String getUserId() {
return userId;
}

public void setUserId(String userId) {
this.userId = userId;
}

public String getShowId() {
return showId;
}

public void setShowId(String showId) {
this.showId = showId;
}

public String getNumberOfSeat() {
return numberOfSeat;
}

public void setNumberOfSeat(String numberOfSeat) {
this.numberOfSeat = numberOfSeat;
}

public String getSeat() {
return seat;
}

public void setSeat(String seat) {
this.seat = seat;
}

}