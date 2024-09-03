package com.app.kalyanbusiness.viewModel;

import android.content.Context;

import com.app.kalyanbusiness.Repo.AuthRepo;
import com.app.kalyanbusiness.Repo.interfaces.ViewLogin;
import com.app.kalyanbusiness.Repo.interfaces.ViewRegister;
import com.app.kalyanbusiness.baseapp.MyBaseApp;
import com.app.kalyanbusiness.baseapp.interfaces.ApiCallback;
import com.app.kalyanbusiness.baseapp.model.BaseResponse;
import com.app.kalyanbusiness.baseapp.viewmodels.BaseViewModel;
import com.app.kalyanbusiness.models.UserModel;
import com.app.kalyanbusiness.requestModel.LoginRequestModel;
import com.app.kalyanbusiness.requestModel.RegisterRequestModel;
import com.app.kalyanbusiness.responseModel.RegisterResponseModel;
import com.google.gson.Gson;

public class RegisterViewModel extends BaseViewModel<ViewRegister> {
    AuthRepo authRepo = new AuthRepo(this);

    @Override
    public Context getContext() {
        return MyBaseApp.getContext();
    }

    public void register(RegisterRequestModel requestModel) {
        new Gson().toJson(requestModel);

        if (requestModel.getName().isEmpty()) {
            view.onNameMissing("Required Name");
        } else if (requestModel.getEmail().isEmpty()) {
            view.onEmailMissing("Required Email");
        } else if (requestModel.getMobile().isEmpty()) {
            view.onEmailMissing("Required Phone Number");
        } else if (requestModel.getMobile().length() < 10 && requestModel.getMobile().length() > 10) {
            view.onPhoneNumberMissing("Invalid Phone Number");
        } else if (requestModel.getPassword().isEmpty()) {
            view.onPasswordMissing("Required Password");
        } else if (requestModel.getPassword().length() < 6) {
            view.onPasswordMissing("Invalid Password");
        } else {
            view.showLoading("Logging In...", false);
            authRepo.register(requestModel, new ApiCallback<BaseResponse<RegisterResponseModel>>(this) {
                @Override
                public void onSuccess(BaseResponse<RegisterResponseModel> data, String msg, int status) {
                    super.onSuccess(data, msg, status);
                    view.onRegisterSuccess(data.getData(), status, msg);
                }
                @Override
                public void onFailure(String msg, int code) {
                    super.onFailure(msg, code);
                    view.onRegisterSuccess(null, code, msg);
                }
            });
        }
    }


}
