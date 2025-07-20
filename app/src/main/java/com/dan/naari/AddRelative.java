package com.dan.naari;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class AddRelative extends AppCompatActivity {

    private static final int REQUEST_CALL = 1;
    DatabaseHelper myDB;
    Button btnAdd,btnView;
    EditText editText, editText2;
    private static final int SEND_SMS_PERMISSION_REQUEST_CODE = 1;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 2;

    private EditText parentNumberEditText;
    private LocationManager locationManager;
    private int count;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_relative);
        editText = (EditText) findViewById(R.id.editText);
        editText2 = (EditText) findViewById(R.id.editText2);
        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnView = (Button) findViewById(R.id.btnView);
        myDB = new DatabaseHelper(this);


        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newEntry = editText.getText().toString();
                String newEntry1 = editText2.getText().toString();
                if(editText.length()!= 0){
                    AddData(newEntry);
                    AddData(newEntry1);
                    editText.setText("");
                }else{
                    Toast.makeText(AddRelative.this, "You must put something in the text field!", Toast.LENGTH_LONG).show();
                }
            }
        });

        btnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddRelative.this, ViewListContents.class);
                startActivity(intent);
            }
        });
    }

    public void AddData(String newEntry) {

        boolean insertData = myDB.addData(newEntry);

        if(insertData==true){
            Toast.makeText(this, "Data Successfully Inserted!", Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(this, "Something went wrong :(.", Toast.LENGTH_LONG).show();
        }

        //Trigger
        parentNumberEditText = findViewById(R.id.editText2);

        // Request SMS permission
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS},
                    SEND_SMS_PERMISSION_REQUEST_CODE);
        }

        // Request location permission
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_PERMISSION_REQUEST_CODE);
        }

        // Initialize location manager
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
    }
    //codeeeeee
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN || keyCode == KeyEvent.KEYCODE_VOLUME_UP) {
            String parentNumber = parentNumberEditText.getText().toString().trim();
            if (!parentNumber.isEmpty()) {
                sendHelpMessage(parentNumber);
                return true;
            } else {
                Toast.makeText(this, "Please enter parent's mobile number", Toast.LENGTH_SHORT).show();
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    private void sendHelpMessage(String phoneNumber) {
        // Get current location
        Location location = getLastKnownLocation();
        if (location != null) {
            double latitude = location.getLatitude();
            double longitude = location.getLongitude();

            // Compose message with location
            String message = "Help me I am in dangerous situation ! My current location is: http://maps.google.com/maps?q=" + latitude + "," + longitude;

            // Send SMS
            try {
                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage(phoneNumber, null, message, null, null);
                Toast.makeText(getApplicationContext(), "Help message sent successfully", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), "Failed to send help message", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        } else {
            Toast.makeText(this, "Unable to retrieve location", Toast.LENGTH_SHORT).show();
        }
    }

    private Location getLastKnownLocation() {
        Location lastKnownLocation = null;
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        }
        return lastKnownLocation;

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == SEND_SMS_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "SMS permission granted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "SMS permission denied", Toast.LENGTH_SHORT).show();
            }
        }
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Location permission granted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Location permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }
//End--------------------
    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {

        int action, keycode;

        action = event.getAction();
        keycode = event.getKeyCode();

        switch (keycode)
        {
            case KeyEvent.KEYCODE_VOLUME_UP:
            {
                if(KeyEvent.ACTION_UP == action){
                    count++;
                    String S1 = String.valueOf(count);
                    Log.d("upButton", S1);
                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:"));
                    if (ActivityCompat.checkSelfPermission(AddRelative.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(AddRelative.this, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL);

                    } else {
                        callIntent.setData(Uri.parse("tel:"));
                        startActivity(callIntent);
                    }
                    startActivity(callIntent);
                }
            }
            case KeyEvent.KEYCODE_VOLUME_DOWN:
                if(KeyEvent.ACTION_DOWN == action){
                    //count = 0;
                    //String S2 = String.valueOf(count);
                    //Log.d("downButton", S2);
                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:"));
                    if (ActivityCompat.checkSelfPermission(AddRelative.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(AddRelative.this, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL);

                    } else {
                        callIntent.setData(Uri.parse("tel:"));
                        startActivity(callIntent);
                    }
                    startActivity(callIntent);
                }
        }
        return super.dispatchKeyEvent(event);
    }
}

