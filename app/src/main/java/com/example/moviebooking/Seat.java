package com.example.moviebooking;


import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Seat implements Parcelable {

@SerializedName("id")
@Expose
private String id;
@SerializedName("show_id")
@Expose
private String showId;
@SerializedName("number")
@Expose
private String number;
//@SerializedName("user_id")
//@Expose
//private String userId;
@SerializedName("status")
@Expose
private String status;

    public Seat(String id, String showId, String number, String status) {
        this.id = id;
        this.showId = showId;
        this.number = number;
//        this.userId = userId;
        this.status = status;
    }

    protected Seat(Parcel in) {
        id = in.readString();
        showId = in.readString();
        number = in.readString();
        status = in.readString();
    }

    public static final Creator<Seat> CREATOR = new Creator<Seat>() {
        @Override
        public Seat createFromParcel(Parcel in) {
            return new Seat(in);
        }

        @Override
        public Seat[] newArray(int size) {
            return new Seat[size];
        }
    };

    public String getId() {
return id;
}

public void setId(String id) {
this.id = id;
}

public String getShowId() {
return showId;
}

public void setShowId(String showId) {
this.showId = showId;
}

public String getNumber() {
return number;
}

public void setNumber(String number) {
this.number = number;
}
//
//public String getUserId() {
//return userId;
//}
//
//public void setUserId(String userId) {
//this.userId = userId;
//}

public String getStatus() {
        return status;
    }

public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(showId);
        parcel.writeString(number);
        parcel.writeString(status);
    }
}