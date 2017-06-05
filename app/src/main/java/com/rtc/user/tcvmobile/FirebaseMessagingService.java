package com.rtc.user.tcvmobile;

import android.annotation.TargetApi;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import com.google.firebase.messaging.RemoteMessage;

import me.leolin.shortcutbadger.ShortcutBadger;


public class FirebaseMessagingService extends com.google.firebase.messaging.FirebaseMessagingService{
    int badgeCount = 0;
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        ShortcutBadger.applyCount(this, ++badgeCount);
        showNotification(remoteMessage.getData().get("message"));
    }

    @TargetApi(Build.VERSION_CODES.N)
    private void showNotification(String message) {

        Intent i = new Intent(this,MainActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,i, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(message))
                .setAutoCancel(true)
                .setContentTitle("Tcv Mobile Pro")
                .setContentText(message)
                .setSound(alarmSound)
                .setVibrate(new long[] { 1000, 1000})
                .setSmallIcon(R.drawable.icon)
                .setContentIntent(pendingIntent);

        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        manager.notify(0,builder.build());
    }


}
