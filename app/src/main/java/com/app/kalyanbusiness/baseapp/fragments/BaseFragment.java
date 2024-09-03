package com.app.kalyanbusiness.baseapp.fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.view.ViewCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.viewbinding.ViewBinding;

import com.app.kalyanbusiness.R;
import com.app.kalyanbusiness.baseapp.activities.BaseActivity;
import com.app.kalyanbusiness.baseapp.interfaces.BaseFragmentMethods;
import com.app.kalyanbusiness.baseapp.presenters.BaseFragmentView;
import com.app.kalyanbusiness.baseapp.utils.AlertUtils;
import com.app.kalyanbusiness.baseapp.utils.Constants;

public abstract class BaseFragment<DB extends ViewBinding> extends Fragment
        implements BaseFragmentMethods, BaseFragmentView {

    public DB mBinding;
    protected AlertDialog.Builder alertDialogBuilder;
    protected AlertDialog alertDialog;
    protected TextView messageTextView;
    protected View dialogView;

    /**
     * if some child fragment is requesting for some permissions and
     * baseFragmentToPassResults !=null, then the results will be passed to this fragment
     * instead of currentFragment of BaseActivity
     */
    protected BaseFragment baseFragmentToPassResults;
    private boolean dismissLoading;

    public void onBackPressed() {
        ((BaseActivity<ViewBinding>) getActivity()).onSuperBackPressed();
    }

    public void initViewModel() {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
//        try {
//            if (getActivity() instanceof BaseFragmentListener)
//                setBaseFragmentListener((BaseFragmentListener) getActivity());
//            else if (getParentFragment() instanceof BaseFragmentListener)
//                setBaseFragmentListener((BaseFragmentListener) getParentFragment());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

    public void loadFragment(BaseFragment baseFragmentChild, int fragmentToLoadLayoutId) {
        baseFragmentChild.setAllowEnterTransitionOverlap(true);
        getChildFragmentManager().beginTransaction().replace(fragmentToLoadLayoutId,
                baseFragmentChild).setCustomAnimations(baseFragmentChild.getEnterAnimation(),
                baseFragmentChild.getExitAnimation(), baseFragmentChild.getPopEnterAnimation(),
                baseFragmentChild.getPopExitAnimation()).commit();
    }

    // Remove Specified Fragment
    public void removeFragment(BaseFragment baseFragmentChild) {
        baseFragmentChild.setAllowEnterTransitionOverlap(true);
        getChildFragmentManager().beginTransaction().remove(baseFragmentChild)
                .setCustomAnimations(baseFragmentChild.getEnterAnimation(),
                        baseFragmentChild.getExitAnimation(), baseFragmentChild.getPopEnterAnimation(),
                        baseFragmentChild.getPopExitAnimation()).commit();
    }

    public void showProgressBar(String loadingText, boolean isCancelable) {
        if (loadingText == null)
            loadingText = "Loading...";

        if (alertDialogBuilder == null) {
            alertDialogBuilder = new AlertDialog.Builder(getContext());

            // Inflate the view only once
            if (dialogView == null) {
                dialogView = LayoutInflater.from(getContext()).inflate(R.layout.loading_layout, null);
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
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null)
            receiveExtras(getArguments());
    }

    public void requestPermissions(BaseFragment requestingFragment,
                                   int requestCode, String[] permissions) {
        if (requestingFragment.isChildFragment())
            ((BaseFragment) getParentFragment()).baseFragmentToPassResults = requestingFragment;
        else
            this.baseFragmentToPassResults = null;
        ((BaseActivity) getActivity()).requestPermissions(requestCode, permissions);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        int layoutId = getRootLayoutId();

        if (layoutId == 0) {
            throw new NullPointerException("No layout id returned in getRootLayoutId to inflate");
        } else {
            if (mBinding == null)
                mBinding = (DB) DataBindingUtil.inflate(inflater, layoutId, container, false);
            return mBinding.getRoot();
        }

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        onLayoutReady(view);
        initViewModel();
        setListeners();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (baseFragmentToPassResults != null)
            baseFragmentToPassResults.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }


    public int getEnterAnimation() {
        return 0;
    }

    public int getExitAnimation() {
        return 0;
    }

    public int getPopEnterAnimation() {
        return 0;
    }

    public int getPopExitAnimation() {
        return 0;
    }

    /**
     * Whenever you set some listeners on some views .e.g do set those listeners in this method,
     * so that it could be easy to find where the listeners are set instead of scrolling through
     * the whole file.
     */
    public void setListeners() {
    }

    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {

        if (nextAnim == R.anim.slide_in_right) {
            ViewCompat.setTranslationZ(getView(), 1f);
        } else {
            ViewCompat.setTranslationZ(getView(), 0f);
        }
        return super.onCreateAnimation(transit, enter, nextAnim);
    }

    public void onLayoutReady(View view) {
    }

    @Override
    public void showLoading(String message, boolean isCancelable) {
        showProgressBar(message,isCancelable);
    }

    @Override
    public void dismissLoading() {
        if (alertDialog != null && alertDialog.isShowing()) {
            alertDialog.dismiss();
        }
    }

    @Override
    public void showError(String error, boolean shouldEndActivity, boolean showToast) {
        dismissLoading();
        try {
            AlertUtils.showSuccessErrorAlert(getActivity(), false, error, shouldEndActivity, -1, showToast);
        } catch (Exception e) {
            e.printStackTrace();
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
            AlertUtils.showSuccessErrorAlert(getActivity(), true, successMessage, shouldEndActivity, requestCode,
                    showToast);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void showNoData() {

    }

    @Override
    public void hideNoData() {
    }

    public void onDataCount(int count) {
        if (count == 0)
            showNoData();
        else
            hideNoData();
    }

    @Override
    public String getFragmentName() {
        return null;
    }

    @Override
    public void onTokenExpired() {

    }

    @Override
    public void onNoInternet() {

    }

    @Override
    public void showToast(String error) {
        if (requireContext() != null)
            Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void exitActivity() {

    }

    @Override
    public void onActivityCreated(@Nullable final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        observeData();
    }

    public void observeData() {
    }

    // Open any file of specified type
    protected void openFile(String filePath, String type) {
        try {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            intent.setDataAndType(Uri.parse(filePath), type);
            requireContext().startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
            showToast("Unable to Open the file");
        }
    }

    // Hides Soft Keyboard
    protected void hideSoftKeyboard(View view) {
        try {
            InputMethodManager inputMethodManager = (InputMethodManager) requireContext().getSystemService(Activity.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}