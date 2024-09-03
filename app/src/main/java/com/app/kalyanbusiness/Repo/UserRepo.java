package com.app.kalyanbusiness.Repo;

import com.app.kalyanbusiness.baseapp.interactors.BaseInteractor;
import com.app.kalyanbusiness.baseapp.interfaces.ApiCallback;
import com.app.kalyanbusiness.baseapp.model.BaseResponse;
import com.app.kalyanbusiness.baseapp.networking.WebServicesHandler;
import com.app.kalyanbusiness.baseapp.repos.BaseRepository;
import com.app.kalyanbusiness.baseapp.utils.Constants;
import com.app.kalyanbusiness.models.SliderModel;
import com.app.kalyanbusiness.models.UserModel;
import com.app.kalyanbusiness.requestModel.LoginRequestModel;
import com.app.kalyanbusiness.requestModel.RegisterRequestModel;
import com.app.kalyanbusiness.requestModel.SupplierRequestModel;
import com.app.kalyanbusiness.responseModel.CategoriesResponseModel;
import com.app.kalyanbusiness.responseModel.SupplierRequestResponseModel;

import java.util.ArrayList;

public class UserRepo extends BaseRepository {

    public UserRepo(BaseInteractor baseInteractor) {
        super(baseInteractor);
    }

    public void updateUser(String requestLogin,String token, ApiCallback<BaseResponse<UserModel>> apiCallback) {
        makeApiCall(WebServicesHandler.getWebServices(token, "1").updateUser(requestLogin), apiCallback);
    }

    public void supplierRequest(SupplierRequestModel requestResponseModel, String token, ApiCallback<BaseResponse<SupplierRequestResponseModel>> apiCallback) {
        makeApiCall(WebServicesHandler.getWebServices(token, "1").supplierRequest(requestResponseModel), apiCallback);
    }
}
