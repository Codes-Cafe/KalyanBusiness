package com.app.kalyanbusiness.SupplierHub;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.app.kalyanbusiness.R;
import com.app.kalyanbusiness.Repo.interfaces.ViewSupplierMain;
import com.app.kalyanbusiness.SupplierHub.fragments.SupplierHomeFragment;
import com.app.kalyanbusiness.SupplierHub.fragments.SupplierInventoryFragment;
import com.app.kalyanbusiness.SupplierHub.fragments.SupplierMenuFragment;
import com.app.kalyanbusiness.SupplierHub.fragments.SupplierOrdersFragment;
import com.app.kalyanbusiness.SupplierHub.fragments.SupplierReturnFragment;
import com.app.kalyanbusiness.baseapp.activities.BaseActivity;
import com.app.kalyanbusiness.baseapp.utils.CacheManager;
import com.app.kalyanbusiness.databinding.ActivitySupplierDashboardBinding;
import com.app.kalyanbusiness.models.UserModel;
import com.app.kalyanbusiness.viewModel.SupplierMainViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class SupplierDashboard extends BaseActivity<ActivitySupplierDashboardBinding> implements ViewSupplierMain {

    Activity activity;
    SupplierMainViewModel mainViewModel;
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
        mainViewModel = new ViewModelProvider(this).get(SupplierMainViewModel.class);
        mainViewModel.setView(this);
    }

    @Override
    public void setListeners() {
        super.setListeners();

        getSupportFragmentManager().beginTransaction().replace(R.id.supplier_frame,
                new SupplierHomeFragment()).commit();
        mBinding.bottomNavSupplier.setOnNavigationItemSelectedListener(nevigationSelected);
    }

    @SuppressLint({"NonConstantResourceId", "SetTextI18n"})
    private final BottomNavigationView.OnNavigationItemSelectedListener nevigationSelected =
            item -> {
                switch (item.getItemId()) {
                    case R.id.item_supplier_home:
                        changeFragment(selectedFragment = new SupplierHomeFragment());
                        setSelectedTab(R.id.item_supplier_home);
                        break;
                    case R.id.item_supplier_orders:
                        changeFragment(selectedFragment = new SupplierOrdersFragment());
                        setSelectedTab(R.id.item_supplier_orders);
                        break;
                    case R.id.item_supplier_returns:
                        changeFragment(selectedFragment = new SupplierReturnFragment());
                        setSelectedTab(R.id.item_supplier_returns);
                        break;
                    case R.id.item_supplier_inventory:
                        changeFragment(selectedFragment = new SupplierInventoryFragment());
                        setSelectedTab(R.id.item_supplier_inventory);
                        break;
                        case R.id.item_supplier_menu:
                        changeFragment(selectedFragment = new SupplierMenuFragment());
                        setSelectedTab(R.id.item_supplier_menu);
                        break;
                }
                if (selectedFragment != null) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.supplier_frame,
                            selectedFragment).commit();
                }
                return true;
            };

    @Override
    public int getRootLayoutId() {
        return R.layout.activity_supplier_dashboard;
    }

    @Override
    public void onUpdateDetails(UserModel data, int statusCode, String msg) {
    }

    public void setSelectedTab(int tabId) {
        mBinding.bottomNavSupplier.setOnNavigationItemSelectedListener(null);
        mBinding.bottomNavSupplier.setSelectedItemId(tabId);
        mBinding.bottomNavSupplier.setOnItemSelectedListener(nevigationSelected);
    }

    public void changeFragment(Fragment fragment) {

        if (fragment instanceof SupplierHomeFragment ||
                fragment instanceof SupplierOrdersFragment ||
                fragment instanceof SupplierReturnFragment ||
                fragment instanceof SupplierInventoryFragment ||
                fragment instanceof SupplierMenuFragment) {
            getSupportFragmentManager().beginTransaction().replace(R.id.supplier_frame,
                    fragment).commit();
            mBinding.bottomNavSupplier.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (mBinding.bottomNavSupplier.getSelectedItemId() == R.id.item_supplier_home) {
            super.onBackPressed();
        } else {
            mBinding.bottomNavSupplier.setSelectedItemId(R.id.item_supplier_home);
        }
    }
}