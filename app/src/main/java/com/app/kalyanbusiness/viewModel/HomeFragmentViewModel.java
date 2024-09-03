package com.app.kalyanbusiness.viewModel;

import android.content.Context;

import com.app.kalyanbusiness.Repo.ProductsRepo;
import com.app.kalyanbusiness.Repo.UserRepo;
import com.app.kalyanbusiness.Repo.interfaces.ViewHomeFragment;
import com.app.kalyanbusiness.baseapp.MyBaseApp;
import com.app.kalyanbusiness.baseapp.interfaces.ApiCallback;
import com.app.kalyanbusiness.baseapp.model.BaseResponse;
import com.app.kalyanbusiness.baseapp.viewmodels.BaseViewModel;
import com.app.kalyanbusiness.models.UserModel;
import com.app.kalyanbusiness.responseModel.CategoriesResponseModel;
import com.google.gson.Gson;

import java.util.ArrayList;

public class HomeFragmentViewModel extends BaseViewModel<ViewHomeFragment> {
    UserRepo userRepo = new UserRepo(this);
    ProductsRepo productsRepo = new ProductsRepo(this);

    @Override
    public Context getContext() {
        return MyBaseApp.getContext();
    }

    public void updateDetails(String userid,String token) {
        new Gson().toJson(userid);

        if (!userid.isEmpty()){
            userRepo.updateUser(userid, token, new ApiCallback<BaseResponse<UserModel>>(this) {
                @Override
                public void onSuccess(BaseResponse<UserModel> data, String msg, int status) {
                    super.onSuccess(data, msg, status);
                    view.onUpdateDetails(data.getData(),status,msg);
                }

                @Override
                public void onFailure(String msg, int code) {
                    super.onFailure(msg, code);
                    view.onUpdateDetails(null,code,msg);
                }
            });
        }
    }

    public void getSliders() {
//            userRepo.getSlider(new ApiCallback<BaseResponse<ArrayList<SliderModel>>>(this) {
//                @Override
//                public void onSuccess(BaseResponse<ArrayList<SliderModel>> data, String msg, int status) {
//                    super.onSuccess(data, msg, status);
//                    view.onFetchSlides(data.getData(),status,msg);
//                }
//
//                @Override
//                public void onFailure(String msg, int code) {
//                    super.onFailure(msg, code);
//                    view.onFetchSlides(null,code,msg);
//                }
//            });
    }


    public void getCategories() {
        productsRepo.getCategories(new ApiCallback<BaseResponse<ArrayList<CategoriesResponseModel>>>(this) {
            @Override
            public void onSuccess(BaseResponse<ArrayList<CategoriesResponseModel>> data, String msg, int status) {
                super.onSuccess(data, msg, status);
                view.onFetchCategories(data.getData(),status,msg);
            }

            @Override
            public void onFailure(String msg, int status) {
                super.onFailure(msg, status);
            }
        });

    }
}
