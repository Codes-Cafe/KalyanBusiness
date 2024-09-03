package com.app.kalyanbusiness.Repo.interfaces;

import com.app.kalyanbusiness.baseapp.presenters.BaseFragmentView;
import com.app.kalyanbusiness.models.UserModel;
import com.app.kalyanbusiness.responseModel.RegisterResponseModel;

public interface ViewRegister extends BaseFragmentView {

    void onRegisterSuccess(RegisterResponseModel registerResponseModel, int statusCode, String msg);

    void onNameMissing(String msg);

    void onPhoneNumberMissing(String msg);

    void onEmailMissing(String msg);

    void onPasswordMissing(String msg);
}
