package com.kseniaser.homework3;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class PowerStatusReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent serviceIntent = new Intent(context, ImageService.class);
        context.startService(serviceIntent);
    }
}
