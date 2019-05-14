package com.kfouri.rappitest.util;

import android.os.Environment;

import java.io.File;

public class Constants {
    public static final String BASE_URL = "https://api.themoviedb.org/";
    public static final String TOKEN = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI4MGJkZDlkMDhlM2UzNzA4YjEzMGE0YTY0YjQ3NTVjOSIsInN1YiI6IjVjZDJlOWIwMGUwYTI2NzRhZGZlYTc1OSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.ewwpYLCThF8W16TAv6WgOcQgMldut-kZBBkKzjF3T68";
    public static final String APIKEY = "80bdd9d08e3e3708b130a4a64b4755c9";
    public static final String IMAGES_URL = "http://image.tmdb.org/t/p/w500";
    public static final String FOLDER_NAME = "rappitest";
    public static final File PATH = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
}
