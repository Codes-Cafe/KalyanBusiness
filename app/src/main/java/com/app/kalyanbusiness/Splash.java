package com.app.kalyanbusiness;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.app.kalyanbusiness.auth.LoginActivity;
import com.app.kalyanbusiness.baseapp.utils.CacheManager;
import com.app.kalyanbusiness.baseapp.utils.Constants;

public class Splash extends AppCompatActivity {

    Activity activity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        activity = this;

        CacheManager.getInstance().setContext(this);

        new Handler()
                .postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (CacheManager.getInstance().getLoginState()){
                            startActivity(new Intent(activity,MainActivity.class));
                        }else{
                            startActivity(new Intent(activity, LoginActivity.class));
                        }
                        finish();
                    }
                },2500);
    }
}