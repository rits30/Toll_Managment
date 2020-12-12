package com.example.toll_management;

import java.io.Serializable;

public class ProfileDto implements Serializable {
    private String name;
    private String email;
    private String mobileNum;

    public ProfileDto(String name, String email, String mobileNum, String vehicleNumber, String vehicleType, Double walletAmount) {
        this.name = name;
        this.email = email;
        this.mobileNum = mobileNum;
        this.vehicleNumber = vehicleNumber;
        this.vehicleType = vehicleType;
        this.walletAmount = walletAmount;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobileNum() {
        return mobileNum;
    }

    public void setMobileNum(String mobileNum) {
        this.mobileNum = mobileNum;
    }

    public String getVehicleNumber() {
        return vehicleNumber;
    }

    public void setVehicleNumber(String vehicleNumber) {
        this.vehicleNumber = vehicleNumber;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public Double getWalletAmount() {
        return walletAmount;
    }

    public void setWalletAmount(Double walletAmount) {
        this.walletAmount = walletAmount;
    }

    private String vehicleNumber;
    private String vehicleType;
    private Double walletAmount;


}
