package com.example.maks.poratunok;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.support.annotation.NonNull;
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

public class MainActivity extends AppCompatActivity {

    //дозвіл на дзвінок
    private static final int REQUEST_CALL=1;
    //оголошую кнопку і текст
    TextView tvText;
    ImageButton btnHelp;
    TextView tvText2;
    TextView tvText3;
    ImageView whiteBarLeft;
    ImageView whiteBarCenter;
    ImageView whiteBarRight;
    ImageView blueBarLeft;
    ImageView blueBarCenter;
    ImageView blueBarRight;
    int i = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActivityCompat.requestPermissions(MainActivity.this,
                new String[]{Manifest.permission.CALL_PHONE},
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

        //кнопка
        btnHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //перше натискання
                if(i==0){
                    tvText.setVisibility(View.INVISIBLE );
                    tvText2.setVisibility(View.VISIBLE);
                    whiteBarLeft.setVisibility(View.INVISIBLE);
                    blueBarLeft.setVisibility(View.VISIBLE);
                    i=1;
                //друге натискання
                }else if(i==1){
                    tvText.setVisibility(View.INVISIBLE );
                    tvText2.setVisibility(View.INVISIBLE);
                    tvText3.setVisibility(View.VISIBLE);
                    whiteBarCenter.setVisibility(View.INVISIBLE);
                    blueBarCenter.setVisibility(View.VISIBLE);
                    i=2;
                //третє натискання
                }else if(i==2){
                    whiteBarRight.setVisibility(View.INVISIBLE);
                    blueBarRight.setVisibility(View.VISIBLE);
                    makeCall();

                }
            }
        });
    }

    //метод дзвінок
    private void makeCall() {
        if (ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[] {Manifest.permission.CALL_PHONE}, REQUEST_CALL);
        }else {
            String dial = "tel:"+"0986455492";
            startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
        }
    }

    //Дозвіл на дзвінок
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {

                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(MainActivity.this, "Permission denied to read your External storage", Toast.LENGTH_SHORT).show();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }
}
