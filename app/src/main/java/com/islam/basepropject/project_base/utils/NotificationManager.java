package com.islam.basepropject.project_base.utils;

import android.app.NotificationChannel;
import android.content.Context;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.islam.basepropject.R;

import java.util.ArrayList;
import java.util.List;

public class NotificationManager {

    public static void initNotificationChannels(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            List<NotificationChannel> channels = new ArrayList<>();

            String[] channelNames = context.getResources().getStringArray(R.array.notifications_channel_names);
            String[] channelDescription = context.getResources().getStringArray(R.array.notifications_channel_description);
            int importance = android.app.NotificationManager.IMPORTANCE_DEFAULT;

            for (int i = 0; i < channelNames.length; i++) {
                NotificationChannel channel = new NotificationChannel(String.valueOf(i), channelNames[i], importance);
                channel.setDescription(channelDescription[i]);
                channels.add(channel);
            }

            android.app.NotificationManager notificationManager = context.getSystemService(android.app.NotificationManager.class);
            notificationManager.createNotificationChannels(channels);
        }
    }

    public static void showNotification(Context context,String channelId,String title, String content)
    {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            channelId = "";
        }

        // Create notification builder.
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, channelId);
        builder.setContentTitle(title);
        builder.setContentText(content);
        builder.setWhen(System.currentTimeMillis());
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setPriority(NotificationCompat.PRIORITY_HIGH);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify((int) System.currentTimeMillis(), builder.build());

    }

}
