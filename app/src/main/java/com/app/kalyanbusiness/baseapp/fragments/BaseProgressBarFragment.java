package com.app.kalyanbusiness.baseapp.fragments;

import android.view.View;
import android.widget.ProgressBar;

import androidx.viewbinding.ViewBinding;

import java.util.HashMap;

public abstract class BaseProgressBarFragment<Vb extends ViewBinding>
        extends BaseFragment<Vb> {

    HashMap<String, ProgressBar> progressBarHashMap = new HashMap<>();

    public void addProgressBar(String key, ProgressBar progressBar) {
        progressBarHashMap.put(key, progressBar);
    }

    public ProgressBar getProgressBar(String key) {
        if (progressBarHashMap.containsKey(key))
            return progressBarHashMap.get(key);
        return null;
    }

    public void showLoading(String key, String message) {
        ProgressBar progressBar = getProgressBar(key);
        if (progressBar != null)
            progressBar.setVisibility(View.VISIBLE);

    }

    public void dismissLoading(String key) {
        ProgressBar progressBar = getProgressBar(key);
        if (progressBar != null)
            progressBar.setVisibility(View.GONE);
    }

    public abstract void addProgressBars();

    @Override
    public void onLayoutReady(View view) {
        super.onLayoutReady(view);
        addProgressBars();
    }
}
