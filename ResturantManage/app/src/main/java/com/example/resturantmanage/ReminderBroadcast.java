package com.example.resturantmanage;

import android.app.Notification;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;


//broadcast receiver class to create push notification
public class ReminderBroadcast extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context,"ResturantManage")
                .setSmallIcon(R.drawable.eat)
                .setContentTitle("Hey Waiter! Reminder!")
                .setContentText("Your Guest are Waiting! Please Take their Orders")
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_HIGH);
        NotificationManagerCompat notificationManager= NotificationManagerCompat.from(context);
        notificationManager.notify(200,builder.build());
    }
}
