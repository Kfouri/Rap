package com.kfouri.rappitest.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class Utils {

    private static final String TAG = "Utils";
    private static final int FILE_QUALITY = 100;

    public static boolean isNetworkAvailable(Context context) {

        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        return networkInfo != null && networkInfo.isConnected();
    }

    public static void deleteRecursive(File fileOrDirectory) {
        Log.d(TAG, "Deleting Folder");
        if (fileOrDirectory.isDirectory()) {
            for (File child : fileOrDirectory.listFiles()) {
                deleteRecursive(child);
            }
        }

        fileOrDirectory.delete();
    }

    public static void createFolder() {
        Log.d(TAG, "Creating Folder");
        File f = new File(Constants.PATH, Constants.FOLDER_NAME);
        if (!f.exists()) {
            if (f.mkdirs()) {
                Log.d(TAG, "Folder " + Constants.PATH + "/" + Constants.FOLDER_NAME + " created");
            }
        }
    }

    public static Boolean saveBitmapToJPEGFile(Bitmap theTempBitmap, File theTargetFile) {
        boolean result = true;
        if (theTempBitmap != null) {
            FileOutputStream out = null;
            try {
                out = new FileOutputStream(theTargetFile);
                theTempBitmap.compress(Bitmap.CompressFormat.JPEG, FILE_QUALITY, out);
            } catch (FileNotFoundException e) {
                result = false;
                e.printStackTrace();
            }
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else {
            result = false;
        }
        return result;
    }

}
