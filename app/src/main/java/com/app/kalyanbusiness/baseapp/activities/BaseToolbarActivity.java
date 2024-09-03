package com.app.kalyanbusiness.baseapp.activities;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.viewbinding.ViewBinding;

public abstract class BaseToolbarActivity<VB extends ViewBinding> extends BaseActivity<VB> {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
  /*      if (getToolbar() != null)
            setSupportActionBar(getToolbar());*/
    }

    public abstract Toolbar getToolbar();
}
