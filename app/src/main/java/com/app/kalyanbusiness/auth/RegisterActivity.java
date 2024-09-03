package com.app.kalyanbusiness.auth;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.app.kalyanbusiness.MainActivity;
import com.app.kalyanbusiness.R;
import com.app.kalyanbusiness.Repo.interfaces.ViewRegister;
import com.app.kalyanbusiness.Splash;
import com.app.kalyanbusiness.baseapp.activities.BaseActivity;
import com.app.kalyanbusiness.baseapp.utils.Constants;
import com.app.kalyanbusiness.baseapp.utils.DialogUtils;
import com.app.kalyanbusiness.baseapp.utils.ViewUtils;
import com.app.kalyanbusiness.databinding.ActivityRegisterBinding;
import com.app.kalyanbusiness.models.UserModel;
import com.app.kalyanbusiness.requestModel.RegisterRequestModel;
import com.app.kalyanbusiness.responseModel.RegisterResponseModel;
import com.app.kalyanbusiness.viewModel.LoginViewModel;
import com.app.kalyanbusiness.viewModel.RegisterViewModel;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class RegisterActivity extends BaseActivity<ActivityRegisterBinding> implements ViewRegister {

    Activity activity;
    RegisterViewModel registerViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimary));
        activity = this;
    }

    @Override
    public int getRootLayoutId() {
        return R.layout.activity_register;
    }

    @Override
    public void initViewModel() {
        super.initViewModel();
        registerViewModel = new ViewModelProvider(this).get(RegisterViewModel.class);
        registerViewModel.setView(this);
    }

    @Override
    public void setListeners() {
        super.setListeners();

        mBinding.password.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_pass, 0, R.drawable.ic_show, 0);
        ViewUtils.setHideShowPassword(mBinding.password);

        mBinding.sendotp.setOnClickListener(view ->{

            if (!mBinding.checkBox.isChecked()){
                Toast.makeText(activity, "Accept Privacy Policy", Toast.LENGTH_SHORT).show();
                return;
            }
            mBinding.tvError.setText("");
            registerViewModel.register(new RegisterRequestModel(
                    mBinding.firstName.getText().toString()
                    ,mBinding.email.getText().toString(),
                    mBinding.phoneNumber.getText().toString(),
                    mBinding.password.getText().toString()));
        });

        mBinding.login.setOnClickListener(view -> startActivity(new Intent(activity,LoginActivity.class)));
    }

    @Override
    public void onRegisterSuccess(RegisterResponseModel registerResponseModel, int statusCode, String msg) {
        if (statusCode == 1){
            DialogUtils.succesDialog(activity, "Success", msg, false, false, "", "Close", new SweetAlertDialog.OnSweetClickListener() {
                @Override
                public void onClick(SweetAlertDialog sweetAlertDialog) {
                    sweetAlertDialog.dismissWithAnimation();
                    startActivity(new Intent(activity, LoginActivity.class));
                    finishAffinity();
                }
            });
        }else if (statusCode == 0){
            mBinding.tvError.setText(String.valueOf(msg));
        }
    }

    @Override
    public void onNameMissing(String msg) {
        mBinding.firstName.setError(String.valueOf(msg));
        mBinding.firstName.requestFocus();
    }

    @Override
    public void onPhoneNumberMissing(String msg) {
        mBinding.phoneNumber.setError(String.valueOf(msg));
        mBinding.phoneNumber.requestFocus();
    }

    @Override
    public void onEmailMissing(String msg) {
        mBinding.email.setError(String.valueOf(msg));
        mBinding.email.requestFocus();
    }

    @Override
    public void onPasswordMissing(String msg) {
        mBinding.password.setError(String.valueOf(msg));
        mBinding.password.requestFocus();
    }
}