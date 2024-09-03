package com.app.kalyanbusiness;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.app.kalyanbusiness.Repo.interfaces.ViewMain;
import com.app.kalyanbusiness.baseapp.activities.BaseActivity;
import com.app.kalyanbusiness.baseapp.utils.CacheManager;
import com.app.kalyanbusiness.databinding.ActivityMainBinding;
import com.app.kalyanbusiness.fragments.CategoriesFragment;
import com.app.kalyanbusiness.fragments.HomeFragment;
import com.app.kalyanbusiness.fragments.MyAccountFragment;
import com.app.kalyanbusiness.fragments.MyOrdersFragment;
import com.app.kalyanbusiness.models.UserModel;
import com.app.kalyanbusiness.viewModel.MainViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends BaseActivity<ActivityMainBinding> implements ViewMain {

    Activity activity;
    MainViewModel mainViewModel;
    Fragment selectedFragment = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = this;
        CacheManager.getInstance().setContext(this);

    }

    @Override
    public void initViewModel() {
        super.initViewModel();
        mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);
        mainViewModel.setView(this);
    }

    @Override
    public void setListeners() {
        super.setListeners();

        UserModel user = CacheManager.getCurrentUser();
        if (user!=null){
            if (user.getName()!=null){
                mBinding.tvName.setText(user.getName());
            }
        }

        getSupportFragmentManager().beginTransaction().replace(R.id.frame,
                new HomeFragment()).commit();
        mBinding.bottomNav.setOnNavigationItemSelectedListener(nevigationSelected);
    }

    @SuppressLint({"NonConstantResourceId", "SetTextI18n"})
    private final BottomNavigationView.OnNavigationItemSelectedListener nevigationSelected =
            item -> {
                switch (item.getItemId()) {
                    case R.id.item_home:
                        changeFragment(selectedFragment = new HomeFragment());
                        setSelectedTab(R.id.item_home);
                        break;
                    case R.id.item_categories:
                        changeFragment(selectedFragment = new CategoriesFragment());
                        setSelectedTab(R.id.item_categories);
                        break;
                    case R.id.item_my_orders:
                        changeFragment(selectedFragment = new MyOrdersFragment());
                        setSelectedTab(R.id.item_my_orders);
                        break;
                    case R.id.item_account:
                        changeFragment(selectedFragment = new MyAccountFragment());
                        setSelectedTab(R.id.item_account);
                        break;
                }
                if (selectedFragment != null) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame,
                            selectedFragment).commit();
                }
                return true;
            };

    @Override
    public int getRootLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void onUpdateDetails(UserModel data, int statusCode, String msg) {
    }

    public void setSelectedTab(int tabId) {
        mBinding.bottomNav.setOnNavigationItemSelectedListener(null);
        mBinding.bottomNav.setSelectedItemId(tabId);
        mBinding.bottomNav.setOnItemSelectedListener(nevigationSelected);
    }

    public void changeFragment(Fragment fragment) {

        if (fragment instanceof HomeFragment ||
                fragment instanceof CategoriesFragment ||
                fragment instanceof MyOrdersFragment ||
                fragment instanceof MyAccountFragment) {
            getSupportFragmentManager().beginTransaction().replace(R.id.frame,
                    fragment).commit();
            mBinding.bottomNav.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (mBinding.bottomNav.getSelectedItemId() == R.id.item_home) {
            super.onBackPressed();
        } else {
            mBinding.bottomNav.setSelectedItemId(R.id.item_home);
        }
    }
}