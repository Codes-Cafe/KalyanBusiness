package com.app.kalyanbusiness.Repo.interfaces;

import com.app.kalyanbusiness.baseapp.presenters.BaseFragmentView;

import com.app.kalyanbusiness.models.SliderModel;
import com.app.kalyanbusiness.models.UserModel;

import java.util.ArrayList;

public interface ViewProfileFragment extends BaseFragmentView {

    void onUpdateDetails(UserModel data,int statusCode,String msg);
}
