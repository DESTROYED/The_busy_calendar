package com.example.destr.busy_calendar.loaders;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v4.util.LruCache;
import android.view.View;
import android.widget.ImageView;

import com.example.destr.busy_calendar.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class ImageLoader {

    private final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
    private final int cacheSize = maxMemory / 8;
    private LruCache<String, Bitmap> mMemoryCache = new LruCache<String, Bitmap>(cacheSize) {

        @Override
        protected int sizeOf(String key, Bitmap value) {
            return value.getByteCount() / 1024;
        }
    };

    private void addBitmapToMemoryCache(String key, Bitmap bitmap) {
        if (getBitmapFromMemCache(key) == null) {
            mMemoryCache.put(key, bitmap);
        }
    }

    private Bitmap getBitmapFromMemCache(String key) {
        return mMemoryCache.get(key);
    }

    public void loadBitmap(String resId, ImageView mImageView) {
        final String imageKey = String.valueOf(resId);

        final Bitmap bitmap = getBitmapFromMemCache(imageKey);
        if (bitmap != null) {
            mImageView.setVisibility(View.VISIBLE);
            mImageView.setImageBitmap(bitmap);
        } else {
            mImageView.setImageResource(R.drawable.ic_download_24dp);
            BitmapWorkerTask task = new BitmapWorkerTask(mImageView);
            task.execute(resId);
        }
    }

    private class BitmapWorkerTask extends AsyncTask<String, Void, Bitmap> {

        private ImageView pImageView;
        BitmapWorkerTask(ImageView pImageView) {
            this.pImageView = pImageView;
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            try {
                URL url = new URL(params[0]);
                HttpURLConnection connection;
                connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream inputStream = connection.getInputStream();
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                addBitmapToMemoryCache(String.valueOf(params[0]), bitmap);
                return bitmap;
            } catch (IOException pE) {
                pE.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(Bitmap pBitmap) {
            pImageView.setImageBitmap(pBitmap);
        }
    }
}
