package com.cornerdesk.esportrealm.notificationServices;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.lifecycle.Lifecycle;

import com.cornerdesk.esportrealm.Home;
import com.cornerdesk.esportrealm.R;

public class Simple_Notification {
    public Simple_Notification(Context ctx, String title, String content){

        NotificationCompat.Builder builder = new NotificationCompat.Builder(ctx, "CHANNEL_ID");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder.setSmallIcon(R.drawable.ic_ongoing);
        } else {
            builder.setSmallIcon(R.drawable.ic_ongoing);
        }

        Intent resultIntent = new Intent(ctx, Home.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(ctx, 1, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        builder.setContentTitle(title);
        builder.setContentText(content);
        //builder.setContentIntent(pendingIntent);
        builder.setOnlyAlertOnce(false);
        builder.setLargeIcon(BitmapFactory.decodeResource(ctx.getResources(),
                R.drawable.ic_ongoing));
        builder.setStyle(new NotificationCompat.BigTextStyle().bigText(content));
        builder.setAutoCancel(true);

        NotificationManager mNotificationManager =
                (NotificationManager) ctx.getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            String channelId = "eSport Realm_Channel";
            NotificationChannel channel = new NotificationChannel(
                    channelId,
                    "eSport Realm",
                    NotificationManager.IMPORTANCE_HIGH);
            mNotificationManager.createNotificationChannel(channel);
            builder.setChannelId(channelId);
        }

        mNotificationManager.notify((int) Math.random(), builder.build());
    }
}
