package com.example.my_application;

import com.google.protobuf.StringValueOrBuilder;

public class Order {
    String o_id;
    String r_id;
    String c_id;
    String v_qty;
    String nv_qty;
    String otp;
    String status;
    String longitude;
    String latitude;
    String requesterName;

    public Order(String o_id, String r_id, String c_id, String v_qty, String nv_qty, String otp, String status, String longitude, String latitude, String requesterName) {
        this.o_id = o_id;
        this.r_id = r_id;
        this.c_id = c_id;
        this.v_qty = v_qty;
        this.nv_qty = nv_qty;
        this.otp = otp;
        this.status = status;
        this.longitude = longitude;
        this.latitude = latitude;
        this.requesterName = requesterName;
    }

    public Order(){

    }

    public String getO_id() {
        return o_id;
    }

    public void setO_id(String o_id) {
        this.o_id = o_id;
    }

    public String getR_id() {
        return r_id;
    }

    public void setR_id(String r_id) {
        this.r_id = r_id;
    }

    public String getC_id() {
        return c_id;
    }

    public void setC_id(String c_id) {
        this.c_id = c_id;
    }

    public String getV_qty() {
        return v_qty;
    }

    public void setV_qty(String v_qty) {
        this.v_qty = v_qty;
    }

    public String getNv_qty() {
        return nv_qty;
    }

    public void setNv_qty(String nv_qty) {
        this.nv_qty = nv_qty;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getRequesterName() {
        return requesterName;
    }

    public void setRequesterName(String requesterName) {
        this.requesterName = requesterName;
    }
}
