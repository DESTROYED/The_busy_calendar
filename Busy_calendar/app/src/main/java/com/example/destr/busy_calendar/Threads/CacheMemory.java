package com.example.destr.busy_calendar.Threads;


import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.util.LruCache;



public class CacheMemory {

    protected void onCreate(Bundle savedInstanceState) {
        final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);

        final int cacheSize = maxMemory / 4;

        mMemoryCache = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap bitmap) {
                return bitmap.getByteCount() / 1024;
            }
        };
    }

    private LruCache<String, Bitmap> mMemoryCache;
    public void addBitmapToMemoryCache(String key, Bitmap bitmap) {
        if (getBitmapFromMemCache(key) == null) {
            mMemoryCache.put(key, bitmap);
        }
    }

    public Bitmap getBitmapFromMemCache(String key) {
        return mMemoryCache.get(key);
    }
}