package com.app.kalyanbusiness.baseapp.activities;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentManager;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.viewbinding.ViewBinding;


import com.app.kalyanbusiness.R;
import com.app.kalyanbusiness.baseapp.fragments.BaseFragment;
import com.app.kalyanbusiness.baseapp.presenters.BaseFragmentView;
import com.app.kalyanbusiness.baseapp.receivers.NetworkStatus;
import com.app.kalyanbusiness.baseapp.utils.AlertUtils;
import com.app.kalyanbusiness.baseapp.utils.Constants;

import java.util.ArrayList;

public abstract class BaseActivity<DB extends ViewBinding> extends AppCompatActivity
        implements BaseFragmentView {

    public DB mBinding;
    private BroadcastReceiver mNetworkReceiver;

    public void toggleToolbarVisibility(boolean isVisible) {
        if (getSupportActionBar() == null)
            return;

        if (isVisible)
            getSupportActionBar().show();
        else
            getSupportActionBar().hide();
    }

    public void showBackArrow() {
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void handleBackArrowClick(MenuItem menuItem) {
        int id = menuItem.getItemId();

        if (id == android.R.id.home)
            onBackPressed();
    }

    private final ViewTreeObserver.OnGlobalLayoutListener keyboardLayoutListener = new ViewTreeObserver.OnGlobalLayoutListener() {
        @Override
        public void onGlobalLayout() {
            int heightDiff = rootLayout.getRootView().getHeight() - rootLayout.getHeight();
            int contentViewTop = getWindow().findViewById(Window.ID_ANDROID_CONTENT).getTop();

            LocalBroadcastManager broadcastManager = LocalBroadcastManager.getInstance(BaseActivity.this);

            if (heightDiff <= contentViewTop) {
                onHideKeyboard();

                Intent intent = new Intent("KeyboardWillHide");
                broadcastManager.sendBroadcast(intent);
            } else {
                int keyboardHeight = heightDiff - contentViewTop;
                onShowKeyboard(keyboardHeight);

                Intent intent = new Intent("KeyboardWillShow");
                intent.putExtra("KeyboardHeight", keyboardHeight);
                broadcastManager.sendBroadcast(intent);
            }
        }
    };

    private boolean keyboardListenersAttached = false;
    private ViewGroup rootLayout;

    protected void onShowKeyboard(int keyboardHeight) {
    }

    protected void onHideKeyboard() {
    }

    protected void attachKeyboardListeners(ViewGroup rootLayout) {
        if (keyboardListenersAttached) {
            return;
        }

        this.rootLayout = rootLayout;

        rootLayout.getViewTreeObserver().addOnGlobalLayoutListener(keyboardLayoutListener);

        keyboardListenersAttached = true;
    }

    @Override
    protected void onDestroy() {
        unregisterNetworkReceiver();
        super.onDestroy();

        if (keyboardListenersAttached)
            rootLayout.getViewTreeObserver().removeGlobalOnLayoutListener(keyboardLayoutListener);
    }

    public void loadFragment(BaseFragment baseFragmentChild, int fragmentToLoadLayoutId) {
        this.currentFragment = baseFragmentChild;
        baseFragmentChild.setAllowEnterTransitionOverlap(true);

        getSupportFragmentManager().beginTransaction().replace(fragmentToLoadLayoutId,
                baseFragmentChild).setCustomAnimations(baseFragmentChild.getEnterAnimation(),
                baseFragmentChild.getExitAnimation(), baseFragmentChild.getPopEnterAnimation(),
                baseFragmentChild.getPopExitAnimation()).commit();
    }

    // Remove Specified Fragment
    public void removeFragment(BaseFragment fragment) {
        fragment.setAllowEnterTransitionOverlap(true);
        getSupportFragmentManager().beginTransaction().remove(fragment)
                .setCustomAnimations(fragment.getEnterAnimation(),
                        fragment.getExitAnimation(), fragment.getPopEnterAnimation(),
                        fragment.getPopExitAnimation()).commit();
    }

    // Pop Out Last Fragment
    public void popOutLastFragment() {
        getSupportFragmentManager().popBackStack();
    }

    public BaseFragment currentFragment;
    protected AlertDialog.Builder alertDialogBuilder;
    protected AlertDialog alertDialog;
    protected TextView messageTextView;
    protected View dialogView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getRootLayoutId() == 0)
            throw new NullPointerException("No root layout id returned in getRootLayoutId. Please return a layout to set it in the onCreate of the BaseActivityClass");
        else
            mBinding = (DB) DataBindingUtil.setContentView(this, getRootLayoutId());

        init();
        mNetworkReceiver = new NetworkStatus();
        registerNetworkReceiver();
    }

    private void registerNetworkReceiver() {
        registerReceiver(mNetworkReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
    }

    protected void unregisterNetworkReceiver() {
        try {
            unregisterReceiver(mNetworkReceiver);
        } catch (Exception e) {
            e.fillInStackTrace();
        }

    }

    protected void init() {
        if (getWindow() != null) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
            getWindow().setStatusBarColor(getResources().getColor(R.color.white));
        }

        findArgs();
        setListeners();
        initViewModel();
        observeData();
        if (getSupportActionBar() != null)
            getSupportActionBar().hide();
    }

    private void findArgs() {
        if (getIntent() != null) {
            Bundle args = getIntent().getExtras();
            if (args != null)
                receiveExtras(args);
        }
    }

    public void observeData() {
    }

    public void initViewModel() {
    }

    public void setListeners() {
    }

    public void showProgressDialog(String loadingText, boolean isCancelable) {
        if (loadingText == null)
            loadingText = "Loading...";

        if (alertDialogBuilder == null) {
            alertDialogBuilder = new AlertDialog.Builder(BaseActivity.this);

            // Inflate the view only once
            if (dialogView == null) {
                dialogView = LayoutInflater.from(BaseActivity.this).inflate(R.layout.loading_layout, null);
                alertDialogBuilder.setView(dialogView);
                messageTextView = dialogView.findViewById(R.id.message);
            }
        }

        messageTextView.setText(loadingText);
        alertDialogBuilder.setCancelable(isCancelable);

        if (alertDialog == null) {
            alertDialog = alertDialogBuilder.create();
        }

        if (!alertDialog.isShowing()) {
            alertDialog.show();
        }
    }

    @Override
    public void dismissLoading() {
        if (alertDialog != null && alertDialog.isShowing()) {
            alertDialog.dismiss();
        }
    }

    @Override
    public void showLoading(String message, boolean isCancelable) {
        showProgressDialog(message, isCancelable);
    }

    @Override
    public void showError(String error, boolean shouldEndActivity, boolean showToast) {
        dismissLoading();
        try {
            AlertUtils.showSuccessErrorAlert(this, false, error, shouldEndActivity, -1, showToast);
        } catch (Exception e) {
            e.fillInStackTrace();
        }
    }

    public String getLoadingText() {
        return "Loading";
    }

    @Override
    public void showSuccessMessage(String message, boolean shouldEndActivity, int requestCode, boolean showToast) {
        String successMessage = null;
        if (message == null || message.isEmpty()) successMessage = Constants.GenericSuccess;
        else successMessage = message;
        try {
            AlertUtils.showSuccessErrorAlert(this, true, successMessage, shouldEndActivity, requestCode,
                    showToast);
        } catch (Exception e) {
            e.fillInStackTrace();
        }
    }

    @Override
    public void onTokenExpired() {

    }

    /**
     * return the root layout to set it in onCreate
     *
     * @return
     */
    public abstract int getRootLayoutId();

    /**
     * returns the toolbar title to be displayed in the toolbar
     *
     * @return
     */

    public String getActivityName() {
        return "";
    }

    /**
     * This method checks that whether all the permission required are already allowed or not
     * if already allowed it will return empty arraylist otherwise arraylist with required
     * permissions
     *
     * @param permissions permissions to check allowance for
     * @return list of not allowed permissions
     */
    public ArrayList<String> checkPermissions(String[] permissions) {
        ArrayList<String> permissionsNotAllowed = new ArrayList<>();
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(BaseActivity.this, permission) != PackageManager.PERMISSION_GRANTED)
                permissionsNotAllowed.add(permission);
        }
        return permissionsNotAllowed;

    }

    public void requestPermissions(int requestCode, String[] permissions) {
        ActivityCompat.requestPermissions(BaseActivity.this, permissions, requestCode);
    }

    /**
     * if you are receiving extras from an activity through intent, Please get those extras
     * in this method. This method is called in the base Activity onCreate. Get all your arguments
     * by the provided arguments variable passed as an argument
     *
     * @param arguments get your data from this variable e.g. arguments.getSringExtra("your code");
     */
    public void receiveExtras(Bundle arguments) {

    }

    public void changeFragment(BaseFragment baseFragment, int layoutId, boolean allowSameFragmentOverlap, boolean addToBackStack) {
        if (!allowSameFragmentOverlap &&
                currentFragment != null && currentFragment.getFragmentName() != null
                && baseFragment.getFragmentName() != null && currentFragment.getFragmentName().equals(baseFragment.getFragmentName()))
            return;

        if (addToBackStack)
            getSupportFragmentManager().beginTransaction()
                    .setCustomAnimations(
                            baseFragment.getEnterAnimation(),
                            this.currentFragment != null ? this.currentFragment.getExitAnimation() : 0,
                            this.currentFragment != null ? this.currentFragment.getPopEnterAnimation() : 0,
                            baseFragment.getPopExitAnimation()).
                    replace(layoutId, baseFragment, baseFragment.getFragmentName()).
                    addToBackStack(baseFragment.getFragmentName()).commit();
        else
            getSupportFragmentManager().beginTransaction()
                    .setCustomAnimations(
                            baseFragment.getEnterAnimation(),
                            this.currentFragment != null ? this.currentFragment.getExitAnimation() : 0,
                            this.currentFragment != null ? this.currentFragment.getPopEnterAnimation() : 0,
                            baseFragment.getPopExitAnimation()).
                    replace(layoutId, baseFragment, baseFragment.getFragmentName())
                    .commit();

        getSupportFragmentManager().executePendingTransactions();
        this.currentFragment = baseFragment;
    }

    public BaseFragment getLatestFragmentFromBackStack() {
        int index = getSupportFragmentManager().getBackStackEntryCount() - 1;
        if (index < 0 || getSupportFragmentManager().getBackStackEntryCount() == 0)
            return null;
        else {
            FragmentManager.BackStackEntry backEntry = getSupportFragmentManager().getBackStackEntryAt(index);
            String tag = backEntry.getName();
            BaseFragment baseFragment = (BaseFragment) getSupportFragmentManager().findFragmentByTag(tag);
            return baseFragment;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (currentFragment != null) {
            currentFragment.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (currentFragment != null)
            currentFragment.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onNoInternet() {

    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        super.onBackPressed();
        if (currentFragment != null){
            currentFragment.onBackPressed();
        }else{
            onSuperBackPressed();
        }

    }

    public void onSuperBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            super.onBackPressed();
            currentFragment = getLatestFragmentFromBackStack();
        } else
            finish();
    }

    @Override
    public void showNoData() {

    }

    @Override
    public void hideNoData() {

    }

    @Override
    public void exitActivity() {

    }

    @Override
    public void showToast(String error) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }

    // Open any file of specified type
    protected void openFile(String filePath, String type) {
        try {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            intent.setDataAndType(Uri.parse(filePath), type);
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
            showToast("Unable to Open the file");
        }
    }


}
