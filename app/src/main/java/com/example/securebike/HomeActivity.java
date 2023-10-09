package com.example.securebike;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import me.itangqi.waveloadingview.WaveLoadingView;

public class HomeActivity extends AppCompatActivity {

    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private TextView status;
    private Switch parkingMode;
    DatabaseReference myRef;
    private Button locate,logout;
    private WaveLoadingView waveLoadingView;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        parkingMode = (Switch)findViewById(R.id.switch2);
        locate = (Button) findViewById(R.id.button_tracker);
        logout=findViewById(R.id.logout);
        status=findViewById(R.id.textView_status);
        waveLoadingView=findViewById(R.id.waveLoadingView);

        myRef = FirebaseDatabase.getInstance().getReference();

        parkingMode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    myRef.child("Member").child("BVXCNUyKXkShyBQBlkWjP3idNyC2").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            dataSnapshot.getRef().child("ParkingMode").setValue("ON");
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {


                        }
                    });
                    Toast.makeText(HomeActivity.this,"Parking Mode is ON", Toast.LENGTH_SHORT).show();
                }
                else{
                    myRef.child("Member").child("BVXCNUyKXkShyBQBlkWjP3idNyC2").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            dataSnapshot.getRef().child("ParkingMode").setValue("OFF");
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {


                        }
                    });
                   
                    Toast.makeText(HomeActivity.this,"Parking Mode is OFF", Toast.LENGTH_SHORT).show();
                }

            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent logoutIntent = new Intent(HomeActivity.this,Main2Activity.class);
                startActivity(logoutIntent);
                finish();
            }
        });
        locate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intoMaps = new Intent(HomeActivity.this,MapsActivity.class);
                startActivity(intoMaps);
            }
        });



    }


    @Override
    protected void onStart() {
        super.onStart();
        myRef.child("Member").child("BVXCNUyKXkShyBQBlkWjP3idNyC2").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String bS = dataSnapshot.child("BikeStatus").getValue(String.class);
                Integer fB = dataSnapshot.child("FuelBar").getValue(Integer.class);
                status.setText((CharSequence) bS);
                waveLoadingView.setProgressValue(fB);
                waveLoadingView.setCenterTitle(String.format("Fuel Level : %d%%",fB));
                if(parkingMode.isChecked()&&bS!="Secured"){
                    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){

                        NotificationChannel channel = new NotificationChannel("SecureBikeNotification","SecureBikeNotification", NotificationManager.IMPORTANCE_HIGH);
                        channel.setDescription("Your Bike is currently being accessed by someone. Click to track the bike");
                        channel.enableVibration(true);
                        channel.enableLights(true);

                        NotificationManager manager = getSystemService(NotificationManager.class);
                        manager.createNotificationChannel(channel);
                    }
                    Intent activityintent = new Intent(HomeActivity.this,MapsActivity.class);
                    PendingIntent contentintent = PendingIntent.getActivity(HomeActivity.this,0,activityintent,0);
                    NotificationCompat.Builder builder = new NotificationCompat.Builder(HomeActivity.this,"SecureBikeNotification")
                            .setContentTitle("Bike Security At Risk!!!")
                            .setContentText("Your Bike is currently being accessed by someone. Click to track the bike")
                            .setSmallIcon(R.drawable.ic_warning_black_24dp)
                            .setAutoCancel(false)
                            .setPriority(NotificationCompat.PRIORITY_HIGH)
                            .setContentIntent(contentintent);

                    NotificationManagerCompat manager =NotificationManagerCompat.from(HomeActivity.this);
                    manager.notify(999,builder.build());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater =getMenuInflater();
        inflater.inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
       switch(item.getItemId()){
           case R.id.logout:{
               FirebaseAuth.getInstance().signOut();
               Intent intoMain2 = new Intent(HomeActivity.this,Main2Activity.class);
               startActivity(intoMain2);
           }
       }
        return super.onOptionsItemSelected(item);
    }
}
