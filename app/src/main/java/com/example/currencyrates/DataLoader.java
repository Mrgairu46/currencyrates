package com.example.currencyrates;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class DataLoader extends AsyncTask<String, Void, List<CurrencyRate>> {

    public interface DataLoaderListener {
        void onDataLoaded(List<CurrencyRate> rates);
        void onError(Exception e);
    }

    private static final String TAG = "DataLoader";

    private final DataLoaderListener listener;
    private Exception error;

    public DataLoader(DataLoaderListener listener) {
        this.listener = listener;
    }

    @Override
    protected List<CurrencyRate> doInBackground(String... params) {
        String urlString = params[0];
        HttpURLConnection connection = null;
        BufferedReader reader = null;

        try {
            URL url = new URL(urlString);
            connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(15000);
            connection.setReadTimeout(15000);
            connection.setRequestMethod("GET");
            connection.connect();

            InputStream stream = connection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(stream));

            StringBuilder buffer = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }

            String json = buffer.toString();
            Log.d(TAG, "JSON Response: " + json);

            return Parser.parseRatesFromJson(json);

        } catch (Exception e) {
            error = e;
            Log.e(TAG, "Error loading currency data", e);
            return null;

        } finally {
            if (connection != null) connection.disconnect();
            if (reader != null) {
                try { reader.close(); } catch (IOException ignored) {}
            }
        }
    }

    @Override
    protected void onPostExecute(List<CurrencyRate> rates) {
        if (error != null) {
            listener.onError(error);
        } else if (rates == null) {
            listener.onError(new RuntimeException("No currency data returned"));
        } else {
            listener.onDataLoaded(rates);
        }
    }
}
