package com.example.toll_management;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class LoginDto implements Serializable {
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getToken() {
        return token;
    }

    public void setToken(Integer token) {
        this.token = token;
    }

    private String email;
    private Integer token;



}
