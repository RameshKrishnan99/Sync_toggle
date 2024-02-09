package com.rk.sync_toggle.service.model;

import com.google.gson.annotations.SerializedName;

public class UserDto {

    @SerializedName("device_id")
    String deviceId;
    @SerializedName("type")
    String type;
    @SerializedName("fcm_token")
    String fcmToken;
    String responseData;

    public UserDto(String deviceId, String type, String fcmToken) {
        this.deviceId = deviceId;
        this.type = type;
        this.fcmToken = fcmToken;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFcmToken() {
        return fcmToken;
    }

    public void setFcmToken(String fcmToken) {
        this.fcmToken = fcmToken;
    }

    public String getResponseData() {
        return responseData;
    }

    public void setResponseData(String responseData) {
        this.responseData = responseData;
    }
}
