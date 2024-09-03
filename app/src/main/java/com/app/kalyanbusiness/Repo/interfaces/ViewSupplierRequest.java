package com.app.kalyanbusiness.Repo.interfaces;

import com.app.kalyanbusiness.baseapp.presenters.BaseFragmentView;
import com.app.kalyanbusiness.models.UserModel;
import com.app.kalyanbusiness.responseModel.SupplierRequestResponseModel;

public interface ViewSupplierRequest extends BaseFragmentView {

    void onRequestResponse(SupplierRequestResponseModel supplierRequestResponseModel, int statusCode, String msg);

    void onMissingKalyanMoneyUserID(String msg);
    void onMissingLeaderCode(String msg);
    void onMissingShopName(String msg);
    void onMissingShopAddress(String msg);
    void onMissingOwnerCode(String msg);
}
