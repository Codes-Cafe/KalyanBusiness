package com.app.kalyanbusiness.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.app.kalyanbusiness.R;
import com.app.kalyanbusiness.Repo.interfaces.ViewProfileFragment;
import com.app.kalyanbusiness.SupplierHub.SupplierDashboard;
import com.app.kalyanbusiness.activities.SuplierRequestActivity;
import com.app.kalyanbusiness.auth.LoginActivity;
import com.app.kalyanbusiness.baseapp.fragments.BaseFragment;
import com.app.kalyanbusiness.baseapp.utils.CacheManager;
import com.app.kalyanbusiness.databinding.FragmentMyAccountBinding;
import com.app.kalyanbusiness.models.UserModel;
import com.app.kalyanbusiness.viewModel.ProfileFragmentViewModel;

import java.util.Objects;


public class MyAccountFragment extends BaseFragment<FragmentMyAccountBinding> implements ViewProfileFragment {

    Activity activity;
    ProfileFragmentViewModel profileFragmentViewModel;


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        activity = (Activity)getContext();

        CacheManager.getInstance().setContext(activity);
    }

    @Override
    public void initViewModel() {
        super.initViewModel();
        profileFragmentViewModel = new ViewModelProvider(this).get(ProfileFragmentViewModel.class);
        profileFragmentViewModel.setView(this);
    }

    @Override
    public void setListeners() {
        super.setListeners();

        mBinding.tvName.setText(Objects.requireNonNull(CacheManager.getCurrentUser()).getName());
        mBinding.supplierTo.setOnClickListener(view -> {
            if (CacheManager.getCurrentUser().getIsVendor().equals("1")){
                startActivity(new Intent(activity, SupplierDashboard.class));
            }else{
                startActivity(new Intent(activity, SuplierRequestActivity.class));
            }
        });

        mBinding.logOut.setOnClickListener(view -> new AlertDialog.Builder(activity).setMessage("Are You Sure You Want To Logout...?")
                .setPositiveButton("Yes", (dialogInterface, i) -> {
                    Toast.makeText(activity, "LogOut Successfully", Toast.LENGTH_SHORT).show();
                    CacheManager.getInstance().setLoginState(false);
                    CacheManager.getInstance().setCurrentUser(null);
                    startActivity(new Intent(activity, LoginActivity.class));
                    activity.finishAffinity();
                }).setNegativeButton("Cancel", (dialogInterface, i) -> dialogInterface.dismiss()).show());
    }

    @Override
    public void onUpdateDetails(UserModel data, int statusCode, String msg) {

    }

    @Override
    public int getRootLayoutId() {
        return R.layout.fragment_my_account;
    }
}