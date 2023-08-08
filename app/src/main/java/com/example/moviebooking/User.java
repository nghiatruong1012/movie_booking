package com.example.moviebooking;


import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.time.LocalDate;
import java.util.ArrayList;


public class User implements Parcelable {

@SerializedName("id")
@Expose
private String id;
@SerializedName("username")
@Expose
private String username;
@SerializedName("password")
@Expose
private String password;
@SerializedName("fullname")
@Expose
private String fullname;
@SerializedName("phone")
@Expose
private String phone;
@SerializedName("email")
@Expose
private String email;
@SerializedName("gt")
@Expose
private String gt;
@SerializedName("birth")
@Expose
private String birth;

    protected User(Parcel in) {
        id = in.readString();
        username = in.readString();
        password = in.readString();
        fullname = in.readString();
        phone = in.readString();
        email = in.readString();
        gt = in.readString();
        birth = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public String getId() {
return id;
}

public void setId(String id) {
this.id = id;
}

public String getUsername() {
return username;
}

public void setUsername(String username) {
this.username = username;
}

public String getPassword() {
return password;
}

public void setPassword(String password) {
this.password = password;
}

public String getFullname() {
return fullname;
}

public void setFullname(String fullname) {
this.fullname = fullname;
}

public String getPhone() {
return phone;
}

public void setPhone(String phone) {
this.phone = phone;
}

public String getEmail() {
return email;
}

public void setEmail(String email) {
this.email = email;
}

public String getGt() {
return gt;
}

public void setGt(String gt) {
this.gt = gt;
}

public String getBirth() {
return birth;
}

public void setBirth(String birth) {
this.birth = birth;
}

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(username);
        parcel.writeString(password);
        parcel.writeString(fullname);
        parcel.writeString(phone);
        parcel.writeString(email);
        parcel.writeString(gt);
        parcel.writeString(birth);
    }
}