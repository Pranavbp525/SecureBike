package com.example.securebike;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import static android.content.ContentValues.TAG;

public class MyForegroundService extends Service {
    public MyForegroundService() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //User defined foreground notification channel
        showNotifications();
        //Thread running in foreground notifications to check database constantly for changes
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Log.d("MYTAG", "run: started");
                while (true) {
//Object creation to access variables from HomeActivity in MyForegroundService
                    HomeActivity obj1 = new HomeActivity();
                    obj1.myRef.child("Member").child("BVXCNUyKXkShyBQBlkWjP3idNyC2").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            String bS = dataSnapshot.child("BikeStatus").getValue(String.class);
                            String fL = dataSnapshot.child("FuelLevel").getValue(String.class);
                            if (bS != "Secured") {
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

                                    NotificationChannel channel = new NotificationChannel("SecureBikeNotification", "SecureBikeNotification", NotificationManager.IMPORTANCE_HIGH);
                                    channel.setDescription("Your Bike is currently being accessed by someone. Click to track the bike");
                                    channel.enableVibration(true);
                                    channel.enableLights(true);

                                    NotificationManager manager = getSystemService(NotificationManager.class);
                                    manager.createNotificationChannel(channel);
                                }
                                Intent activityintent = new Intent(MyForegroundService.this, MapsActivity.class);
                                PendingIntent contentintent = PendingIntent.getActivity(MyForegroundService.this, 0, activityintent, 0);
                                NotificationCompat.Builder builder = new NotificationCompat.Builder(MyForegroundService.this, "SecureBikeNotification")
                                        .setContentTitle("Bike Security At Risk!!!")
                                        .setContentText("Your Bike is currently being accessed by someone. Click to track the bike")
                                        .setSmallIcon(R.drawable.ic_warning_black_24dp)
                                        .setAutoCancel(false)
                                        .setPriority(NotificationCompat.PRIORITY_HIGH)
                                        .setContentIntent(contentintent);

                                NotificationManagerCompat manager = NotificationManagerCompat.from(MyForegroundService.this);
                                manager.notify(999, builder.build());

                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                }
            }


        });
        thread.start();
        return START_STICKY;
    }
//foreground notification channel creation
    private void showNotifications() {
        Intent serviceIntent=new Intent(MyForegroundService.this,HomeActivity.class);
        PendingIntent pintent=PendingIntent.getActivity(MyForegroundService.this,0,serviceIntent,0);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this,"channelid");
        builder.setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentText("This is a Service. Click to open the app")
                .setContentTitle("SecureBike Service")
                .setContentIntent(pintent);
        Notification notification= builder.build();
        startForeground(123,notification);
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
