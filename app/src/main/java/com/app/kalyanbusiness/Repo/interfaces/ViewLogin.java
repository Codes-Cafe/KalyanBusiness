package com.app.kalyanbusiness.Repo.interfaces;

import com.app.kalyanbusiness.baseapp.presenters.BaseFragmentView;
import com.app.kalyanbusiness.models.UserModel;

public interface ViewLogin extends BaseFragmentView {

    void onLoginSuccess(UserModel data, int statusCode, String msg);

    void onEmailMissing(String msg);

    void onPasswordMissing(String msg);
}
