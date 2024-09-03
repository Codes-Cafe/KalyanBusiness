package com.app.kalyanbusiness.baseapp.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class BaseResponse<T> implements Serializable {

    @SerializedName("message")
    String message;
    @SerializedName("status")
    int status;
    @SerializedName("data")
    T data;

    public BaseResponse(String message, int status, T data) {
        this.message = message;
        this.status = status;
        this.data = data;
    }

    public BaseResponse() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
