package com.kseniaser.homework3;

import android.app.IntentService;
import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class ImageService extends IntentService {

    public static String IMAGE_RECEIVED = "com.kseniaser.image_received";

    public ImageService() {
        super(ImageService.class.getName());
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        try {
            URL url = new URL(getString(R.string.image_url));
            Bitmap bitmap = BitmapFactory.decodeStream(url.openStream());
            FileOutputStream fileStream = openFileOutput(getString(R.string.image_filename), MODE_PRIVATE);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileStream);
            sendBroadcast(new Intent(IMAGE_RECEIVED));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
