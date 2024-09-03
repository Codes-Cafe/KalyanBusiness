package com.app.kalyanbusiness.baseapp.interfaces;


import com.app.kalyanbusiness.baseapp.interactors.BaseInteractor;

public abstract class ApiCallback<T> {

    BaseInteractor baseInteractor;

    public ApiCallback(BaseInteractor baseInteractor) {
        this.baseInteractor = baseInteractor;
    }

//    public abstract void onSuccess(T data, String msg, int status);
    public void onSuccess(T data, String msg, int status){
        if (baseInteractor != null)
            baseInteractor.onSuccess();
    }

    public void onFailure(String msg, int status) {
        if (baseInteractor != null)
            baseInteractor.onFailure(msg, status);
    }

}
