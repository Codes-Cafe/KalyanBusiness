package com.app.kalyanbusiness.baseapp.interfaces;

import android.os.SystemClock;
import android.util.Log;
import android.view.View;

public abstract class PreventViewMultiClicks implements View.OnClickListener {
    private long lastClickTime = 0;
    public abstract void onSingleClicked(View v);

    @Override
    public void onClick(View v) {
        if (SystemClock.elapsedRealtime() - lastClickTime <= 1000)
            return;

        lastClickTime = SystemClock.elapsedRealtime();
        onSingleClicked(v);
    }
}
