package com.app.kalyanbusiness.Repo.interfaces;

import com.app.kalyanbusiness.baseapp.presenters.BaseFragmentView;
import com.app.kalyanbusiness.models.SliderModel;
import com.app.kalyanbusiness.models.UserModel;
import com.app.kalyanbusiness.responseModel.CategoriesResponseModel;

import java.util.ArrayList;

public interface ViewHomeFragment extends BaseFragmentView {

    void onUpdateDetails(UserModel data,int statusCode,String msg);
    void onFetchSlides(ArrayList<SliderModel> list, int statusCode, String msg);
    void onFetchCategories(ArrayList<CategoriesResponseModel> list, int statusCode, String msg);
}
