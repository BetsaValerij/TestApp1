package com.t.testapp.async;

import android.os.AsyncTask;

import com.t.testapp.service.XmlPullParserHandler;
import com.t.testapp.model.BookItem;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class LoadBookAsyncTask extends AsyncTask<String, Void, List<BookItem>> {
    private List<BookItem> bookItemList;
    @Override
    protected void onPreExecute(){
        bookItemList = new ArrayList<>();
    }
    @Override
    protected List<BookItem> doInBackground(String... strings) {
        URL url = null;
        try {
            url = new URL(strings[0]);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.connect();

            if (urlConnection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                return null;
            }
            XmlPullParserHandler xmlPullParserHandler = new XmlPullParserHandler();
            bookItemList = xmlPullParserHandler.parse(urlConnection.getInputStream());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return bookItemList;
    }
}
