package com.app.kalyanbusiness.baseapp;

import android.content.Context;

public class MyBaseApp {

    private static Context context;

    public static void setContext(Context context) {
        MyBaseApp.context = context;
    }

    public static Context getContext() {
        return context;
    }
}
