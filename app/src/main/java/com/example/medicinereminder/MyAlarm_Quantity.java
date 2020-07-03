package com.example.medicinereminder;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.provider.Settings;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

public class MyAlarm_Quantity extends BroadcastReceiver {

    static MediaPlayer mediaPlayer;

    @Override
    public void onReceive(Context context, Intent intent) {
        mediaPlayer=MediaPlayer.create(context, Settings.System.DEFAULT_RINGTONE_URI);

        mediaPlayer.start();
        Toast.makeText(context,"Alarm is playing",Toast.LENGTH_SHORT).show();

        Intent stopintent = new Intent(context,StopAlarm_Quantity.class);
        stopintent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent stoppending = PendingIntent.getActivity(context, 0 ,stopintent,PendingIntent.FLAG_ONE_SHOT);


        NotificationCompat.Builder builder = new NotificationCompat.Builder(context,"0");
        builder.setAutoCancel(true)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Medicine Reminder")
                .setContentText("This medicine is about to end")
                .setDefaults(Notification.DEFAULT_LIGHTS)
                .setContentInfo("Medicine Reminder");
        builder.addAction(R.drawable.ic_launcher_background,"Stop",stoppending);

        NotificationManager manager = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
        manager.notify(1,builder.build());


    }
}
