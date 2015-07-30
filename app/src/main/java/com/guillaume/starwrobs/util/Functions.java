package com.guillaume.starwrobs.util;

import android.support.annotation.NonNull;


public class Functions {

    public static int getIdFromUrl(@NonNull String url) {
        return Integer.parseInt(url.substring(url.length()-2, url.length()-1));
    }
}
