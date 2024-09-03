package com.app.kalyanbusiness.Repo;

import com.app.kalyanbusiness.baseapp.interactors.BaseInteractor;
import com.app.kalyanbusiness.baseapp.interfaces.ApiCallback;
import com.app.kalyanbusiness.baseapp.model.BaseResponse;
import com.app.kalyanbusiness.baseapp.networking.WebServicesHandler;
import com.app.kalyanbusiness.baseapp.repos.BaseRepository;
import com.app.kalyanbusiness.baseapp.utils.Constants;
import com.app.kalyanbusiness.models.UserModel;
import com.app.kalyanbusiness.requestModel.LoginRequestModel;
import com.app.kalyanbusiness.requestModel.RegisterRequestModel;
import com.app.kalyanbusiness.responseModel.RegisterResponseModel;

public class AuthRepo extends BaseRepository {

    public AuthRepo(BaseInteractor baseInteractor) {
        super(baseInteractor);
    }

    public void login(LoginRequestModel requestLogin, ApiCallback<BaseResponse<UserModel>> apiCallback) {
        makeApiCall(WebServicesHandler.getWebServices("", "1").login(requestLogin), apiCallback);
    }

    public void register(RegisterRequestModel registerRequestModel, ApiCallback<BaseResponse<RegisterResponseModel>> apiCallback) {
        makeApiCall(WebServicesHandler.getWebServices("","1").register(registerRequestModel), apiCallback);
    }

}
