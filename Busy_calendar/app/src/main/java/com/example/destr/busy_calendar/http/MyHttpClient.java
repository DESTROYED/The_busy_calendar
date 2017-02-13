package com.example.destr.busy_calendar.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class MyHttpClient {

    public String method(final String url,final String method) {
        final String response;
        InputStream inputStream = null;

        try {
            final URL reqUrl = new URL(url);
            final HttpsURLConnection connection = (HttpsURLConnection) reqUrl.openConnection();
            connection.setRequestMethod(method);
            inputStream = connection.getInputStream();
            final BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            final StringBuilder builder = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }

            response = builder.toString();

            return response;
        } catch (final IOException e) {
            return null;
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (final IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
