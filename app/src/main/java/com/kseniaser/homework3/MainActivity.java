package com.kseniaser.homework3;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements ImageViewer {

    private ImageView mImage;
    private TextView mNotLoaded;

    private BroadcastReceiver receiver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mImage = (ImageView) findViewById(R.id.image);
        mNotLoaded = (TextView) findViewById(R.id.not_loaded);

        showImage();

        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                showImage();
            }
        };
        registerReceiver(receiver, new IntentFilter(ImageService.IMAGE_RECEIVED));
    }

    private void showImage() {
        new LoadImageAsyncTask(this).execute();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }

    @Override
    public void setImage(Bitmap bitmap) {
        if (bitmap != null) {
            mImage.setVisibility(View.VISIBLE);
            mNotLoaded.setVisibility(View.GONE);
            mImage.setImageBitmap(bitmap);
        } else {
            mImage.setVisibility(View.GONE);
            mNotLoaded.setVisibility(View.VISIBLE);
        }

    }

    private class LoadImageAsyncTask extends AsyncTask<Void, Void, Bitmap> {

        private ImageViewer mViewer;

        LoadImageAsyncTask(ImageViewer viewer) {
            this.mViewer = viewer;
        }

        @Override
        protected Bitmap doInBackground(Void... voids) {
            return BitmapFactory.decodeFile(getFilesDir().getPath() + "/image.png");
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            mViewer.setImage(bitmap);
        }
    }
}
