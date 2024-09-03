package com.app.kalyanbusiness.baseapp.interactors;

public interface BaseInteractor {
    void onSuccess();

    void onFailure(String error, Integer status);

//    default void onNoInternet() {
//
//    }
//
//    void onDataLoaded(T data);
//
//    void onTokenExpired();
//
//    default void onFinishWithError(String error) {
//
//    }
//
//    default void onDataCount(int count){}


}
