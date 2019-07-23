package bloodbank.android.example.com.bloodbank.service;

import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;


import com.bumptech.glide.load.model.ModelLoader;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.Gson;

import java.util.Random;

import bloodbank.android.example.com.bloodbank.R;
import bloodbank.android.example.com.bloodbank.ui.activity.HomeNaigationDrawerActivity;


public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private static final String NOTIFICATION_ID_EXTRA = "notificationId";
    private static final String IMAGE_URL_EXTRA = "imageUrl";
    private static final String ADMIN_CHANNEL_ID = "admin_channel";
    private NotificationManager notificationManager;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
       // String lang = LoadStringData((Activity) getApplicationContext(), USER_LANG);
        //You should use an actual ID instead
        int notificationId = new Random().nextInt(60000);

        String massage = remoteMessage.getData().toString();

        //ActivityData data = new ActivityData();

        try {
            Gson gson = new Gson();
            //data = gson.fromJson(massage, NotificationRealTime.class).getData();
        } catch (Exception t) {

        }

        String Title = getString(R.string.notification_title);
        String Content = getString(R.string.notification_title1) + " ";

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            setupChannels();
        }

        Bitmap logo = BitmapFactory.decodeResource(getResources(), R.drawable.notification);
        Intent resultIntent = new Intent(this, HomeNaigationDrawerActivity.class);
        resultIntent.putExtra("data", massage);  // for extra data if needed..
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(HomeNaigationDrawerActivity.class);

// Adds the Intent that starts the Activity to the top of the stack
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent =  stackBuilder.getPendingIntent(0,PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, ADMIN_CHANNEL_ID)
                .setSmallIcon(R.drawable.notification)
                .setContentTitle(Title)
                .setContentText(Content)
                .setLargeIcon(logo)
                .setColor(ContextCompat.getColor(this, R.color.colorPrimary))
                .setStyle(new NotificationCompat.BigTextStyle().bigText(Title))
                .setStyle(new NotificationCompat.BigTextStyle().bigText(Content).setSummaryText("#hashtag"))
                .setShowWhen(true)
                .setSound(defaultSoundUri)
                .setContentIntent(resultPendingIntent)
                .setAutoCancel(true);

        notificationManager.notify(notificationId, notificationBuilder.build());

    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setupChannels() {
        CharSequence adminChannelName = ("");
        String adminChannelDescription = "";

        NotificationChannel adminChannel;
        adminChannel = new NotificationChannel(ADMIN_CHANNEL_ID, adminChannelName, NotificationManager.IMPORTANCE_LOW);
        adminChannel.setDescription(adminChannelDescription);
        adminChannel.enableLights(true);
        adminChannel.setLightColor(Color.RED);
        adminChannel.enableVibration(true);
        if (notificationManager != null) {
            notificationManager.createNotificationChannel(adminChannel);
        }
    }
}