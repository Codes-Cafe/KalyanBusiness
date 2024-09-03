package com.app.kalyanbusiness.baseapp.utils;

import android.content.Context;

import androidx.annotation.NonNull;


import com.app.kalyanbusiness.models.UserModel;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

public class CacheManager {
    private static final String KEY_USER = "key_user";
    Context context;
    TinyDB tinyDB;
    Gson gson;

    private static final CacheManager instance = new CacheManager();

    public void setContext(Context context) {
        this.context = context;
        tinyDB = new TinyDB(context);
        gson = new Gson();
    }

    public static CacheManager getInstance() {
        return instance;
    }

    public void setCurrentUser(UserModel user) {
        String json = gson.toJson(user);
        tinyDB.putString(KEY_USER, json);
    }

    public static UserModel getCurrentUser() {
        if (getInstance().tinyDB == null || getInstance().gson == null)
            return null;
        String json = getInstance().tinyDB.getString(KEY_USER);
        UserModel user = null;
        if (json != null)
            user = getInstance().gson.fromJson(json, UserModel.class);

        return user;
    }

    // Remember User's Auth Credentials
    public void saveRememberMe(boolean rememberMe, int roleId, @NotNull String authPhoneNumber, @NotNull String authPassword, @NonNull String timeZone) {
        if (tinyDB == null)
            tinyDB = new TinyDB(context);

        if (!rememberMe) {
            authPassword = "";
            authPhoneNumber = "";
        }

        String keyTimeZone = "key_time_zone";
        String keyRememberMe = "key_patient_auth_remember";
        String keyAuthPassword = "key_patient_auth_password";
        String keyAuthPhoneNumber = "key_patient_auth_phone";

        if (roleId == 2) { // Doctor Role
            keyRememberMe = "key_doctor_auth_remember";
            keyAuthPassword = "key_doctor_auth_password";
            keyAuthPhoneNumber = "key_doctor_auth_phone";
        } else if (roleId == 5) { // Pharmacy User Role
            keyRememberMe = "key_pharmacy_user_auth_remember";
            keyAuthPassword = "key_pharmacy_user_auth_password";
            keyAuthPhoneNumber = "key_pharmacy_user_auth_phone";
        }

        tinyDB.putInt("key_role_id_" + roleId, roleId);
        tinyDB.putBoolean(keyRememberMe, rememberMe);
        tinyDB.putString(keyAuthPassword, authPassword);
        tinyDB.putString(keyAuthPhoneNumber, authPhoneNumber);
        tinyDB.putString(keyTimeZone, timeZone);
    }

    public String getSavedTimeZone() {
        if (tinyDB == null)
            tinyDB = new TinyDB(context);
        return tinyDB.getString("key_time_zone");
    }

    // Save Authentication Token in Pref
    public void saveAuthToken(String token) {
        UserModel user = getCurrentUser();
        if (user != null)
            user.setToken(token);

        setCurrentUser(user);
    }

    private void ensureInitialized() {
        if (tinyDB == null) {
            throw new IllegalStateException("CacheManager not initialized. Call setContext() before using this method.");
        }
    }

    public boolean getLoginState() {
        ensureInitialized();
        return tinyDB.getBoolean("is_logged_in");
    }

    public void setLoginState(boolean decision) {
        tinyDB.putBoolean("is_logged_in",decision);
    }
}