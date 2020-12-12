package com.example.toll_management;

public class RangeStatus {
   private String responseType;

    public String getResponseType() {
        return responseType;
    }

    public void setResponseType(String responseType) {
        this.responseType = responseType;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public double getTollCharges() {
        return tollCharges;
    }

    public void setTollCharges(double tollCharges) {
        this.tollCharges = tollCharges;
    }

    public double getCurrentBalance() {
        return currentBalance;
    }

    public void setCurrentBalance(double currentCharges) {
        this.currentBalance = currentCharges;
    }

    private boolean status;
   private double tollCharges;
   private double currentBalance;
}
