package com.example.destr.busy_calendar.imageLoad;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class ImageLoader extends AsyncTask<String, Void, Bitmap> {

    private ImageView image;

    public ImageLoader(ImageView image) {
        this.image = image;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        image.setImageBitmap(bitmap);
    }

    @Override
    protected Bitmap doInBackground(String... params) {
        try {
            URL url = new URL(params[0]);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream inputStream = connection.getInputStream();
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            return bitmap;
        } catch (NullPointerException e) {
            Log.d("EXception!", String.valueOf(e));
            return null;
        } catch (MalformedURLException e) {
            e.printStackTrace();
            Log.d("EXception!", String.valueOf(e));
            return null;
        } catch (IOException e) {
            Log.d("EXception!", String.valueOf(e));
            e.printStackTrace();
            return null;
        }

    }

}