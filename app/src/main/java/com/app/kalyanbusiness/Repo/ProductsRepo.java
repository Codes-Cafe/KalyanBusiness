package com.app.kalyanbusiness.Repo;

import com.app.kalyanbusiness.baseapp.interactors.BaseInteractor;
import com.app.kalyanbusiness.baseapp.interfaces.ApiCallback;
import com.app.kalyanbusiness.baseapp.model.BaseResponse;
import com.app.kalyanbusiness.baseapp.networking.WebServicesHandler;
import com.app.kalyanbusiness.baseapp.repos.BaseRepository;
import com.app.kalyanbusiness.responseModel.CategoriesResponseModel;

import java.util.ArrayList;

public class ProductsRepo extends BaseRepository{


    public ProductsRepo(BaseInteractor baseInteractor) {
        super(baseInteractor);
    }

    public void getCategories(ApiCallback<BaseResponse<ArrayList<CategoriesResponseModel>>> apiCallback) {
        makeApiCall(WebServicesHandler.getWebServices("", "1").getCategories(), apiCallback);
    }
}
