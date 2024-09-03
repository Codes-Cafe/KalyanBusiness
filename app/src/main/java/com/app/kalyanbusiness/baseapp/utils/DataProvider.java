package com.app.kalyanbusiness.baseapp.utils;

import android.annotation.SuppressLint;

import com.app.kalyanbusiness.R;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;


public class DataProvider {

    @SuppressLint("CheckResult")
    public static RequestOptions getGlideRequestOptions(boolean circularImage, boolean shouldOverrideDimensions) {
        RequestOptions requestOptions = new RequestOptions();
        if (circularImage)
            requestOptions.circleCrop();
        if (shouldOverrideDimensions) {

            requestOptions.override(200, 200);
        }

        requestOptions.diskCacheStrategy(DiskCacheStrategy.ALL);

        //requestOptions.placeholder(R.drawable.image_placeholder);
        requestOptions.error(R.drawable.ic_baseline_image_24);
        return requestOptions;
    }


}
