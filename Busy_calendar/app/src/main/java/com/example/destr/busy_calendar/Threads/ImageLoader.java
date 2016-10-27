package com.example.destr.busy_calendar.Threads;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class ImageLoader{

    private ImageView image;
    CacheMemory mCacheMemory=new CacheMemory();
    public ImageLoader(ImageView image,String url) {
            new getFromCache().execute(url);
            this.image = image;
    }
    private class getFromCache extends AsyncTask<String,Void,Bitmap>{
        @Override
        protected void onPostExecute(Bitmap bitmap) {
                image.setImageBitmap(bitmap);
        }
        @Override
        protected Bitmap doInBackground(String... params) {
            Bitmap bitmap = mCacheMemory.getBitmapFromMemCache(params[0]);
            if (bitmap==null){
                try {

                    bitmap= getBitmapFromUrl(params);
                    mCacheMemory.addBitmapToMemoryCache(params[0],bitmap);
                    return bitmap ;

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return bitmap;
        }
    }

    private static Bitmap getBitmapFromUrl(String[] params) throws IOException {
        URL url;
        url = new URL(params[0]);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setDoInput(true);
        connection.connect();
        InputStream inputStream = connection.getInputStream();
        return BitmapFactory.decodeStream(inputStream);
    }
}


