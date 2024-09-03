package com.app.kalyanbusiness.Repo.interfaces;

import com.app.kalyanbusiness.baseapp.presenters.BaseFragmentView;
import com.app.kalyanbusiness.models.UserModel;

public interface ViewMain extends BaseFragmentView {

    void onUpdateDetails(UserModel data,int statusCode,String msg);
}
