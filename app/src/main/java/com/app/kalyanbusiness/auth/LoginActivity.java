package com.app.kalyanbusiness.auth;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.app.kalyanbusiness.MainActivity;
import com.app.kalyanbusiness.R;
import com.app.kalyanbusiness.Repo.interfaces.ViewLogin;
import com.app.kalyanbusiness.baseapp.activities.BaseActivity;
import com.app.kalyanbusiness.baseapp.utils.CacheManager;
import com.app.kalyanbusiness.baseapp.utils.Constants;
import com.app.kalyanbusiness.baseapp.utils.ViewUtils;
import com.app.kalyanbusiness.databinding.ActivityLoginBinding;
import com.app.kalyanbusiness.models.UserModel;
import com.app.kalyanbusiness.requestModel.LoginRequestModel;
import com.app.kalyanbusiness.viewModel.LoginViewModel;
import com.google.gson.Gson;

public class LoginActivity extends BaseActivity<ActivityLoginBinding> implements ViewLogin {

    Activity activity;
    LoginViewModel loginViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimary));
        activity = this;

        CacheManager.getInstance().setContext(this);
    }

    @Override
    public void initViewModel() {
        super.initViewModel();
        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        loginViewModel.setView(this);
    }

    @Override
    public void setListeners() {
        super.setListeners();


        mBinding.password.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_pass, 0, R.drawable.ic_show, 0);
        ViewUtils.setHideShowPassword(mBinding.password);

        mBinding.login.setOnClickListener(view -> {
            mBinding.tvError.setText("");
            loginViewModel.login(
                    new LoginRequestModel(mBinding.email.getText().toString().trim(),
                            mBinding.password.getText().toString().trim()));
        });

        mBinding.register.setOnClickListener(view -> startActivity(new Intent(activity, RegisterActivity.class)));
    }

    @Override
    public int getRootLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void onLoginSuccess(UserModel data, int statusCode, String msg) {
        if (statusCode == 1){
            if (data != null){
                Log.e("data", "onLoginSuccess: "+data.getName());
                CacheManager.getInstance().setLoginState(true);
                CacheManager.getInstance().setCurrentUser(data);
                startActivity(new Intent(activity, MainActivity.class));
                finishAffinity();
            }
        } else if (statusCode == 0){
            mBinding.tvError.setText(String.valueOf(msg));
        }
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