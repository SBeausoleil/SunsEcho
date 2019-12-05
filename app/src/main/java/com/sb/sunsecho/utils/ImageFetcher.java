package com.sb.sunsecho.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.InputStream;
import java.net.URL;
import java.util.function.Consumer;

public class ImageFetcher extends AsyncTask<URL, Void, Bitmap> {

    private Consumer<Bitmap> receiver;

    public ImageFetcher(Consumer<Bitmap> receiver) {
        this.receiver = receiver;
    }

    @Override
    protected Bitmap doInBackground(URL... urls) {
        try {
            InputStream in = urls[0].openStream();
            return BitmapFactory.decodeStream(in);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(Bitmap result) {
        receiver.accept(result);
    }
}
