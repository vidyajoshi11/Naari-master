package com.dan.naari;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    int count = 0;
//Bundle use to passing the data
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Bundle extras = getIntent().getExtras();
        //String V1 = extras.getString(Intent.EXTRA_TEXT);
        //Log.d("NumberMainActivity", V1);
        TextView T1 = findViewById(R.id.T1);
        T1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, AddRelative.class);
                startActivity(i);
            }

        });

        //HELPLINE NUMBER

    }
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Bundle extras = getIntent().getExtras();
        String V1 = extras.getString(Intent.EXTRA_TEXT);
        Log.d("NumberMainActivity",V1);
    }


    //   public void myProfile(View v){
//        Intent i = new Intent(getApplicationContext(), MyProfile.class);
//        startActivity(i);
//    }
    public void myClickHandler(View view) {
        // Your implementation here
        // This method will be called when the button is clicked
    }

    public void addRelative(View v) {
        Intent i = new Intent(getApplicationContext(), AddRelative.class);
        startActivity(i);
    }

    public void helplineNumbers(View v) {
        Intent i = new Intent(getApplicationContext(), helplineCall.class);
        startActivity(i);
    }
    public void developedBy(View v) {
        Intent i = new Intent(getApplicationContext(), DeveloperByActivity.class);
        startActivity(i);
    }

    public void HowTo(View v) {
        Intent i = new Intent(getApplicationContext(), HowToSwipe.class);
        startActivity(i);
    }
    public void Swap(View v) {
        Intent i = new Intent(getApplicationContext(), Swapping.class);
        startActivity(i);
    }
    public void record(View v) {
        Intent i = new Intent(getApplicationContext(), Record.class);
        startActivity(i);
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {

        int action, keycode;

        action = event.getAction();
        keycode = event.getKeyCode();
        switch (keycode) {
            case KeyEvent.KEYCODE_VOLUME_UP: {
                if (KeyEvent.ACTION_UP == action) {
                    count++;
                    String S1 = String.valueOf(count);
                    Log.d("upButton", S1);
                }
            }
            case KeyEvent.KEYCODE_VOLUME_DOWN:
                if (KeyEvent.ACTION_DOWN == action) {
                    count = 0;
                    String S2 = String.valueOf(count);
                    Log.d("downButton", S2);
                }

                return super.dispatchKeyEvent(event);
        }
        return false;
    }


//HELPLINE NUMBER FOR GIRL

    private static final int REQUEST_READ_CONTACTS = 79;
    private ArrayList<String> contactList;
    private ArrayAdapter<String> adapter;
    private MediaPlayer mediaPlayer;


    private void loadContacts() {
        // Load contacts logic
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_VOLUME_DOWN || keyCode == KeyEvent.KEYCODE_VOLUME_UP)) {
            startPoliceSound();
            initiateCall();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void startPoliceSound() {
        mediaPlayer = MediaPlayer.create(this, R.raw.police);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();
    }

    private void initiateCall() {
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        // Change this to your registered contact number
        String registeredContactNumber = "103";
        callIntent.setData(android.net.Uri.parse("tel:" + registeredContactNumber));
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_READ_CONTACTS);
            return;
        }
        startActivity(callIntent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_READ_CONTACTS) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                initiateCall();
            } else {
                Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}



