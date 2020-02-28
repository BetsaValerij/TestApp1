package com.t.testapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.res.Configuration;
import android.os.Bundle;

import com.t.testapp.async.LoadBookAsyncTask;
import com.t.testapp.model.BookItem;
import com.t.testapp.service.Util;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {
    private static final String BOOK_URL = "http://www.lukaspetrik.cz/filemanager/tmp/reader/data.xml";
    RecyclerView rvBook;
    BookGridAdapter adapter;
    List<BookItem> arrayList = new ArrayList<>();
    int gridColumn = 0;
    StaggeredGridLayoutManager staggeredGridLayoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {
            arrayList = new LoadBookAsyncTask().execute(BOOK_URL).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        adapter = new BookGridAdapter(this, arrayList);
        rvBook = (RecyclerView) findViewById(R.id.rv_book);
        gridColumn = Util.calculateGridColumns(this, 180);
        staggeredGridLayoutManager = new StaggeredGridLayoutManager(gridColumn, //number of grid columns
                GridLayoutManager.VERTICAL);
        rvBook.setLayoutManager(staggeredGridLayoutManager);
        rvBook.setAdapter(adapter);

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Checks the orientation of the screen
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            gridColumn = Util.calculateGridColumns(this, 180);
            staggeredGridLayoutManager.setSpanCount(gridColumn);

        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            gridColumn = Util.calculateGridColumns(this, 200);
            staggeredGridLayoutManager.setSpanCount(gridColumn);
        }
    }

}
