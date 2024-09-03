package com.app.kalyanbusiness.baseapp.repos;

import android.annotation.SuppressLint;
import android.util.Log;


import com.app.kalyanbusiness.baseapp.interactors.BaseInteractor;
import com.app.kalyanbusiness.baseapp.interfaces.ApiCallback;
import com.app.kalyanbusiness.baseapp.model.BaseResponse;

import java.io.File;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;


public class BaseRepository {

    public BaseInteractor interactor;

    public BaseRepository(BaseInteractor baseInteractor) {
        this.interactor = baseInteractor;
    }

    @SuppressLint("CheckResult")
    protected <E> void makeApiCall(Single<E> single, ApiCallback<E> apiCallback) {
        single.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe((userBaseResponse, throwable)
                -> {
            if (apiCallback == null)
                return;
            onApiResponse(userBaseResponse, apiCallback, throwable);
        });

    }

    public <E> void onApiResponse(E userBaseResponse, ApiCallback<E> apiCallback, Throwable throwable) {
        if (userBaseResponse != null) {
            if (userBaseResponse instanceof BaseResponse) {
                if (((BaseResponse<?>) userBaseResponse).getStatus() != 1) {
                    apiCallback.onFailure(((BaseResponse<?>) userBaseResponse).getMessage(),
                            ((BaseResponse<?>) userBaseResponse).getStatus());
                    return;
                }

            }

            try {
                assert userBaseResponse instanceof BaseResponse;
                BaseResponse baseResponse = (BaseResponse) userBaseResponse;
                apiCallback.onSuccess(userBaseResponse, baseResponse.getMessage(), baseResponse.getStatus());
            } catch (Exception e) {
                e.fillInStackTrace();
                Log.e("TAG", "_onApiResponse_Exception: " + e.getMessage());
                apiCallback.onFailure(e.getMessage(), -2);
            }
        } else if (throwable != null) {
            apiCallback.onFailure(throwable.getMessage(), -1);
        }
    }

    // Convert given file to Multipart Body
    protected MultipartBody.Part createMultipartFile(String name, File file) {
        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        return MultipartBody.Part.createFormData(name, file.getName(), requestBody);
    }
}
