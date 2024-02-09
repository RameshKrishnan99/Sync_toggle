package com.rk.sync_toggle.service.model;

import com.google.gson.annotations.SerializedName;

public class MessageDto {
    @SerializedName("message")
    String requestData;
    String success;
    String failure;


    public String getFailure() {
        return failure;
    }

    public void setFailure(String failure) {
        this.failure = failure;
    }

    public String getRequestData() {
        return requestData;
    }

    public void setRequestData(String requestData) {
        this.requestData = requestData;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }
}
