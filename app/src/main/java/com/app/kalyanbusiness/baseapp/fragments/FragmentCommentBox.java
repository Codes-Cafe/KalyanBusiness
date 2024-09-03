package com.app.kalyanbusiness.baseapp.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.app.kalyanbusiness.databinding.FragmentCommentBoxBinding;
import com.app.kalyanbusiness.baseapp.utils.TextUtils;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class FragmentCommentBox extends BottomSheetDialogFragment {
    private IFragmentCommentBox listener;
    private FragmentCommentBoxBinding mBinding;

    private final static String KEY_TITLE = "key_title";
    private final static String KEY_MSG = "key_msg";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = FragmentCommentBoxBinding.inflate(inflater, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setCancelable(true);
        String title = "", msg = "";

        try {
            assert getArguments() != null;
            title = getArguments().getString(KEY_TITLE);
            msg = getArguments().getString(KEY_MSG);
        }catch (AssertionError | Exception e) {
            e.fillInStackTrace();
        }

        mBinding.titleTv.setText(title);
        mBinding.msgTv.setText(msg);

        clickEvent();
    }

    // Handle Click Events
    private void clickEvent() {
        mBinding.saveBtn.setOnClickListener((View.OnClickListener) v -> {
            String comment = mBinding.commentBoxEt.getText().toString().trim();
            if (TextUtils.isStringEmpty(comment))
                mBinding.commentBoxEt.setError("Say something");
            else {
                mBinding.commentBoxEt.setError(null);
                if (listener != null)
                    listener.onCommented(comment);
            }
        });
    }

    // Return initialized Bundle Ref
    public Bundle initBundle(String title, String msg) {
        Bundle bundle = new Bundle();
        bundle.putString(KEY_TITLE, title);
        bundle.putString(KEY_MSG, msg);

        return bundle;
    }

    public void setCommentBoxListener(IFragmentCommentBox listener) {
        this.listener = listener;
    }

    // Interface Comment Box Listener
    public interface IFragmentCommentBox {
        void onCommented(String comment);
    }
}
