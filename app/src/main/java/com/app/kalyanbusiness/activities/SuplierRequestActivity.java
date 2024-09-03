package com.app.kalyanbusiness.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import androidx.lifecycle.ViewModelProvider;

import com.app.kalyanbusiness.R;
import com.app.kalyanbusiness.Repo.interfaces.ViewSupplierRequest;
import com.app.kalyanbusiness.baseapp.activities.BaseActivity;
import com.app.kalyanbusiness.baseapp.utils.CacheManager;
import com.app.kalyanbusiness.baseapp.utils.DialogUtils;
import com.app.kalyanbusiness.databinding.ActivitySuplierRequestBinding;
import com.app.kalyanbusiness.models.UserModel;
import com.app.kalyanbusiness.requestModel.SupplierRequestModel;
import com.app.kalyanbusiness.responseModel.SupplierRequestResponseModel;
import com.app.kalyanbusiness.viewModel.SupplierRequestViewModel;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class SuplierRequestActivity extends BaseActivity<ActivitySuplierRequestBinding> implements ViewSupplierRequest {


    Activity activity;
    SupplierRequestViewModel supplierRequestViewModel;
    UserModel userModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = this;

        CacheManager.getInstance().setContext(activity);
    }

    @Override
    public void initViewModel() {
        super.initViewModel();
        supplierRequestViewModel = new ViewModelProvider(this).get(SupplierRequestViewModel.class);
        supplierRequestViewModel.setView(this);
    }

    @Override
    public void setListeners() {
        super.setListeners();

        userModel = CacheManager.getCurrentUser();

        mBinding.back.setOnClickListener(view -> onBackPressed());

        mBinding.lySendRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                supplierRequestViewModel.makeRequest(new SupplierRequestModel(
                        "" + mBinding.etMemberId.getText().toString(),
                        "" + mBinding.etLeaderCode.getText().toString(),
                        "" + mBinding.etShopName.getText().toString(),
                        "" + mBinding.etShopAddress.getText().toString(),
                        "" + mBinding.etOwnerName.getText().toString()
                        , "" + userModel.getUserid()));
            }
        });
    }

    @Override
    public int getRootLayoutId() {
        return R.layout.activity_suplier_request;
    }

    @Override
    public void onRequestResponse(SupplierRequestResponseModel requestResponseModel, int statusCode, String msg) {
        if (statusCode == 1) {
            DialogUtils.succesDialog(activity, "Success", "Vendor Request Submitted"
                    , false, false, "", "Close", new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            sweetAlertDialog.dismissWithAnimation();
                            finish();
                        }
                    });
        } else if (statusCode == 0) {
            mBinding.tvError.setText(String.valueOf(msg));
        }
    }

    @Override
    public void onMissingKalyanMoneyUserID(String msg) {
        mBinding.etMemberId.setError(String.valueOf(msg));
        mBinding.etMemberId.requestFocus();
    }

    @Override
    public void onMissingLeaderCode(String msg) {
        mBinding.etLeaderCode.setError(String.valueOf(msg));
        mBinding.etLeaderCode.requestFocus();
    }

    @Override
    public void onMissingShopName(String msg) {
        mBinding.etShopName.setError(String.valueOf(msg));
        mBinding.etShopName.requestFocus();
    }

    @Override
    public void onMissingShopAddress(String msg) {
        mBinding.etShopAddress.setError(String.valueOf(msg));
        mBinding.etShopAddress.requestFocus();
    }

    @Override
    public void onMissingOwnerCode(String msg) {
        mBinding.etOwnerName.setError(String.valueOf(msg));
        mBinding.etOwnerName.requestFocus();
    }
}