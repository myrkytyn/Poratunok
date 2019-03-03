package com.example.maks.poratunok;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    //дозвіл на дзвінок
    private static final int REQUEST_CALL=1;
    //оголошую кнопку і текст
    TextView tvText;
    ImageButton btnHelp;
    TextView tvText2;
    TextView tvText3;
    int i = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Пошук елеменів
        tvText = (TextView) findViewById(R.id.tvText);
        tvText2 = (TextView) findViewById(R.id.tvText2);
        tvText3 = (TextView) findViewById(R.id.tvText3);
        btnHelp = (ImageButton) findViewById(R.id.btnHelp);

        //кнопка
        btnHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //перше натискання
                if(i==0){
                    tvText.setVisibility(View.INVISIBLE );
                    tvText2.setVisibility(View.VISIBLE);
                    i=1;
                //друге натискання
                }else if(i==1){
                    tvText.setVisibility(View.INVISIBLE );
                    tvText2.setVisibility(View.INVISIBLE);
                    tvText3.setVisibility(View.VISIBLE);
                    i=2;
                //третє натискання
                }else if(i==2){
                    makeCall();
                }
            }
        });
    }

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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == REQUEST_CALL){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                makeCall();
            }
        }
    }
}
