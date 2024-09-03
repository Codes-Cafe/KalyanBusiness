package com.app.kalyanbusiness.baseapp.utils;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.TypedValue;

import java.util.List;

public class GenericUtils {

    public static Uri createFileForDownload(Context context, String fileName) {
        String mimeType = "application/pdf";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {

            final ContentValues values = new ContentValues();
            final ContentResolver resolver = context.getContentResolver();
            values.put(MediaStore.MediaColumns.DISPLAY_NAME, fileName);
            values.put(MediaStore.MediaColumns.MIME_TYPE, mimeType);

            final Uri contentUri = MediaStore.Files.getContentUri("external");
            values.put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DOWNLOADS);

            // create a package provider string suggested by the error message.
            return resolver.insert(contentUri, values);
        }
        else {
            Intent intent = new Intent(Intent.ACTION_CREATE_DOCUMENT);
            // will trigger exception if no  appropriate category passed
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            // or whatever mimeType you want
            intent.setType(mimeType);
            intent.putExtra(Intent.EXTRA_TITLE, fileName);
            ((Activity) context).startActivityForResult(intent, 200);
        }

        return null;
    }

    public static int getAttributedColor(Context context, int color) {
        TypedValue typedValue = new TypedValue();
        Resources.Theme theme = context.getTheme();
        theme.resolveAttribute(color, typedValue, true);
        return typedValue.data;
    }

    public static boolean isStringEmpty(String string) {
        return string == null || string.trim().isEmpty() || string.equalsIgnoreCase("null");
    }

    public static boolean isArrayEmpty(List value) {
        return value == null || value.isEmpty();
    }
}
