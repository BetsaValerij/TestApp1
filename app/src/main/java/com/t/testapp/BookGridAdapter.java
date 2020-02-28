package com.t.testapp;


import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.t.testapp.async.DownloadImageTask;
import com.t.testapp.model.BookItem;

import java.util.List;
import java.util.concurrent.ExecutionException;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class BookGridAdapter extends RecyclerView.Adapter<BookGridAdapter.ViewHolder> {
    private List<BookItem> bookItemArrayList;
    private LayoutInflater lInflater;
    private Context mCtx;

    public BookGridAdapter(Context context, List<BookItem> list){
        this.mCtx = context;
        this.lInflater = LayoutInflater.from(context);
        this.bookItemArrayList = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layoutView = lInflater
                .inflate(R.layout.book_item, parent, false);

        return new ViewHolder(layoutView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final BookItem item = getBook(position);
        if (!item.getTitle().isEmpty())
            holder.txtTitle.setText(item.getTitle());
        Bitmap bmp = null;
        if (!item.getUrl().isEmpty()) {
            try {
                bmp = new DownloadImageTask().execute(item.getUrl()).get();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if (bmp!= null) {
            holder.imgIcon.setImageBitmap(bmp);
        } else {
            holder.imgIcon.setImageResource(R.drawable.ic_empty_image_96dp);
        }
        if (item.getIs_new().equalsIgnoreCase("true")) {
            holder.itemNew.setVisibility(View.VISIBLE);
        }
        DisplayMetrics displaymetrics = new DisplayMetrics();
        ((Activity) mCtx).getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int imgWidth = displaymetrics.widthPixels / 3;
        int imgHeight = displaymetrics.heightPixels / 4;
        holder.imgIcon.getLayoutParams().width = imgWidth;
        holder.imgIcon.getLayoutParams().height = imgHeight;
    }

    @Override
    public int getItemCount() {
        return bookItemArrayList.size();
    }
    public void setBookItemArrayList(List<BookItem> books){
        this.bookItemArrayList = books;
    }
    private BookItem getBook(int position){
        return (BookItem)bookItemArrayList.get(position);
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView txtTitle;
        public ImageView imgIcon;
        public RelativeLayout itemNew;
        ViewHolder(View view) {
            super(view);
            txtTitle = (TextView)view.findViewById(R.id.book_title);
            imgIcon = (ImageView)view.findViewById(R.id.book_img);
            itemNew = (RelativeLayout)view.findViewById(R.id.item_new_book);
        }
    }
}
