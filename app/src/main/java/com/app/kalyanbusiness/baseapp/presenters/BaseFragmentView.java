package com.app.kalyanbusiness.baseapp.presenters;

public interface BaseFragmentView {
    void showLoading(String message, boolean isCancelable);

    void dismissLoading();

    void showError(String error, boolean shouldEndActivity, boolean showToast);

    void showSuccessMessage(String message, boolean shouldEndActivity, int requestCode, boolean showToast);

    void onTokenExpired();

    default void onFinishWithError(String error) {

    }

    void onNoInternet();

    default void showNoData() {

    }

    default void hideNoData() {

    }

    default String getNoDataText() {
        return "No data found";
    }

    void exitActivity();

    void showToast(String error);
}
