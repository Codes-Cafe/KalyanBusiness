package com.app.kalyanbusiness.viewModel;

import android.content.Context;

import com.app.kalyanbusiness.Repo.AuthRepo;
import com.app.kalyanbusiness.Repo.UserRepo;
import com.app.kalyanbusiness.Repo.interfaces.ViewLogin;
import com.app.kalyanbusiness.Repo.interfaces.ViewSupplierRequest;
import com.app.kalyanbusiness.baseapp.MyBaseApp;
import com.app.kalyanbusiness.baseapp.interfaces.ApiCallback;
import com.app.kalyanbusiness.baseapp.model.BaseResponse;
import com.app.kalyanbusiness.baseapp.viewmodels.BaseViewModel;
import com.app.kalyanbusiness.models.UserModel;
import com.app.kalyanbusiness.requestModel.LoginRequestModel;
import com.app.kalyanbusiness.requestModel.SupplierRequestModel;
import com.app.kalyanbusiness.responseModel.SupplierRequestResponseModel;
import com.google.gson.Gson;

public class SupplierRequestViewModel extends BaseViewModel<ViewSupplierRequest> {
    UserRepo userRepo = new UserRepo(this);

    @Override
    public Context getContext() {
        return MyBaseApp.getContext();
    }

    public void makeRequest(SupplierRequestModel requestViewModel) {
        new Gson().toJson(requestViewModel);

        if (requestViewModel.getKm_userid().isEmpty()) {
            view.onMissingKalyanMoneyUserID("Required ID");
        } else if (requestViewModel.getLeader_code().isEmpty()) {
            view.onMissingLeaderCode("Required Leader Code");
        } else if (requestViewModel.getShop_name().isEmpty()) {
            view.onMissingShopName("Required Shop Name");
        } else if (requestViewModel.getShop_address().isEmpty()) {
            view.onMissingShopAddress("Required Address");
        } else if (requestViewModel.getOwner_name().isEmpty()) {
            view.onMissingOwnerCode("Required Owner Name");
        } else {
            view.showLoading("Logging In...", false);
            userRepo.supplierRequest(requestViewModel, "", new ApiCallback<BaseResponse<SupplierRequestResponseModel>>(this) {
                @Override
                public void onSuccess(BaseResponse<SupplierRequestResponseModel> data, String msg, int status) {
                    super.onSuccess(data, msg, status);
                    view.onRequestResponse(data.getData(),status,msg);
                }

                @Override
                public void onFailure(String msg, int status) {
                    super.onFailure(msg, status);
                    view.onRequestResponse(null,status,msg);
                }
            });
        }
    }


}
