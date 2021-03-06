package com.example.maks.poratunok;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;

public class MainActivity extends AppCompatActivity {

    //дозвіл на дзвінок
    private static final int REQUEST_CALL = 1;
    private static final int REQUEST_LOCATION = 2;
    //оголошую кнопку і текст
    TextView tvText; //натисніть, щоб викликати допомогу
    ImageButton btnHelp; //центральна кнопка
    TextView tvText2; //натисніть ще раз
    TextView tvText3; //натисніть востаннє
    ImageView whiteBarLeft; //action bar
    ImageView whiteBarCenter; //action bar
    ImageView whiteBarRight; //action bar
    ImageView blueBarLeft; //action bar
    ImageView blueBarCenter; //action bar
    ImageView blueBarRight; //action bar
    int i = 0; //рахунок натисків
    //до GPS
    TextView gpsCoordinates;
    TextView networkCoordinates;
    private LocationManager locationManager;

    //створення аплікухи
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        //перевірка дозволів
        ActivityCompat.requestPermissions(MainActivity.this,
                new String[]{Manifest.permission.CALL_PHONE, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.INTERNET},
                1);

        //Пошук елеменів
        tvText = (TextView) findViewById(R.id.tvText);
        tvText2 = (TextView) findViewById(R.id.tvText2);
        tvText3 = (TextView) findViewById(R.id.tvText3);
        btnHelp = (ImageButton) findViewById(R.id.btnHelp);
        whiteBarLeft = (ImageView) findViewById(R.id.WhiteBarLeft);
        whiteBarCenter = (ImageView) findViewById(R.id.WhiteBarCenter);
        whiteBarRight = (ImageView) findViewById(R.id.WhiteBarRight);
        blueBarLeft = (ImageView) findViewById(R.id.BlueBarLeft);
        blueBarCenter = (ImageView) findViewById(R.id.BlueBarCenter);
        blueBarRight = (ImageView) findViewById(R.id.BlueBarRight);
        //до GPS


        //кнопка
        btnHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //перше натискання
                if (i == 0) {
                    tvText.setVisibility(View.INVISIBLE);
                    tvText2.setVisibility(View.VISIBLE);
                    whiteBarLeft.setVisibility(View.INVISIBLE);
                    blueBarLeft.setVisibility(View.VISIBLE);
                    i = 1;
                    //друге натискання
                } else if (i == 1) {
                    tvText.setVisibility(View.INVISIBLE);
                    tvText2.setVisibility(View.INVISIBLE);
                    tvText3.setVisibility(View.VISIBLE);
                    whiteBarCenter.setVisibility(View.INVISIBLE);
                    blueBarCenter.setVisibility(View.VISIBLE);
                    i = 2;
                    //третє натискання
                } else if (i == 2) {
                    whiteBarRight.setVisibility(View.INVISIBLE);
                    blueBarRight.setVisibility(View.VISIBLE);
                    location();
                    setContentView(R.layout.activity_location);
                    gpsCoordinates = (TextView) findViewById(R.id.gpsCoordinates);
                    networkCoordinates = (TextView) findViewById(R.id.networkCoordinates);
                    makeCall();
                }
            }
        });
    }

    //метод дзвінок
    private void makeCall() {
        if (ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL);
        } else {
            String dial = "tel:" + "0986455492";
            startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
        }
    }

    //
    void location() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Toast toast = Toast.makeText(this, "Permission failed", Toast.LENGTH_SHORT);
            toast.show();
            return;
        }


        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                1000 * 10, 10, locationListener);
        locationManager.requestLocationUpdates(
                LocationManager.NETWORK_PROVIDER, 1000 * 10, 10,
                locationListener);
    }

    private LocationListener locationListener = new LocationListener() {

        @Override
        public void onLocationChanged(Location location) {
            showLocation(location);
        }

        @Override
        public void onProviderDisabled(String provider) {
            startActivity(new Intent(
                    android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
        }

        @Override
        public void onProviderEnabled(String provider) {
            // Here, thisActivity is the current activity
            if ((ContextCompat.checkSelfPermission(MainActivity.this,
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) && (ContextCompat.checkSelfPermission(MainActivity.this,
                    Manifest.permission.ACCESS_COARSE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED))
            {
                // Permission is not granted
                // Should we show an explanation?
                if ((ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
                        Manifest.permission.ACCESS_FINE_LOCATION)) && (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
                        Manifest.permission.ACCESS_COARSE_LOCATION))) {
                    // Show an explanation to the user *asynchronously* -- don't block
                    // this thread waiting for the user's response! After the user
                    // sees the explanation, try again to request the permission.
                } else {
                    // No explanation needed; request the permission
                    ActivityCompat.requestPermissions(MainActivity.this,
                            new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                            1);

                    // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                    // app-defined int constant. The callback method gets the
                    // result of the request.
                }
            } else {
                // Permission has already been granted
            }

            showLocation(locationManager.getLastKnownLocation(provider));
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
        }
    };

    private void showLocation(Location location) {
        if (location == null)
            return;
        if (location.getProvider().equals(LocationManager.GPS_PROVIDER)) {
            gpsCoordinates.setText(formatLocation(location));
        } else if (location.getProvider().equals(
                LocationManager.NETWORK_PROVIDER)) {
            networkCoordinates.setText(formatLocation(location));
        }
    }

    private String formatLocation(Location location) {
        if (location == null)
            return "";
        return String.format(
                "Coordinates: lat = %1$.4f, lon = %2$.4f, time = %3$tF %3$tT",
                location.getLatitude(), location.getLongitude(), new Date(
                        location.getTime()));
    }
    //

    //Дозвіл на дзвінок
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CALL: {

                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(MainActivity.this, "Ви заборонили робити дзвінки", Toast.LENGTH_SHORT).show();
                }
                return;
            }
            case REQUEST_LOCATION: {

                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(MainActivity.this, "Ви заборонили доступ до місцезнаходження", Toast.LENGTH_SHORT).show();
                }
                return;
            }


            // other 'case' lines to check for other
            // permissions this app might request
        }
    }
}

