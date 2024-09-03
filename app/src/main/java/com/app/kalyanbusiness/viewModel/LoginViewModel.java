package com.app.kalyanbusiness.viewModel;

import android.content.Context;

import com.app.kalyanbusiness.Repo.AuthRepo;
import com.app.kalyanbusiness.Repo.interfaces.ViewLogin;
import com.app.kalyanbusiness.baseapp.MyBaseApp;
import com.app.kalyanbusiness.baseapp.interfaces.ApiCallback;
import com.app.kalyanbusiness.baseapp.model.BaseResponse;
import com.app.kalyanbusiness.baseapp.viewmodels.BaseViewModel;
import com.app.kalyanbusiness.models.UserModel;
import com.app.kalyanbusiness.requestModel.LoginRequestModel;
import com.google.gson.Gson;

public class LoginViewModel extends BaseViewModel<ViewLogin> {
    AuthRepo authRepo = new AuthRepo(this);

    @Override
    public Context getContext() {
        return MyBaseApp.getContext();
    }

    public void login(LoginRequestModel requestLogin) {
        new Gson().toJson(requestLogin);

        if (requestLogin.getPhoneNumber().isEmpty()) {
            view.onEmailMissing("Required Phone Number");
        } else if (requestLogin.getPhoneNumber().length() < 10 && requestLogin.getPhoneNumber().length() > 10) {
            view.onEmailMissing("Invalid Phone Number");
        } else if (requestLogin.getPassword().isEmpty()) {
            view.onPasswordMissing("Required Password");
        } else if (requestLogin.getPassword().length() < 6) {
            view.onPasswordMissing("Invalid Password");
        } else {
            view.showLoading("Logging In...", false);
            authRepo.login(requestLogin, new ApiCallback<BaseResponse<UserModel>>(this) {
                @Override
                public void onSuccess(BaseResponse<UserModel> data, String msg, int status) {
                    super.onSuccess(data, msg, status);
                    view.onLoginSuccess(data.getData(), status, msg);
                }
                @Override
                public void onFailure(String msg, int code) {
                    super.onFailure(msg, code);
                    view.onLoginSuccess(null, code, msg);
                }
            });
        }
    }


}
