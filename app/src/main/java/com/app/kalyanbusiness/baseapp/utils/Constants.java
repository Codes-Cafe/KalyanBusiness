package com.app.kalyanbusiness.baseapp.utils;

import androidx.lifecycle.MutableLiveData;

public class Constants {
    public static final String TYPE_IMAGE = "image/*";
    public static final String TYPE_VIDEO = "video/*";
    public static final String TYPE_PDF = "application/pdf";
    public static final String FILE_PROVIDER = "com.app.kalyanmoney.fileprovider";

    private Constants() throws IllegalAccessException {
        throw new IllegalAccessException("Access Restricted!");
    }


    public static String BaseApis = "https://kalyanmoney.com/";


    public static final int PICK_FILES = 123;
    public static final String Login_Kalyan_Business = "kalyan_business_login";

    public static final String BLOB_PATH = "https://alcstorages.blob.core.windows.net";
    public static String GenericSuccess = "";

    public static String EXTERNAL_STORAGE_DIRECTORY_SENT_FILES = "";
    public static String MAIN_PROGRESS = "main_progress";
    //public static final MutableLiveData<Boolean> isStreamDropped = new MutableLiveData<>();
    public static final MutableLiveData<Boolean> isVideoCallDurationCompleted = new MutableLiveData<>();
}
