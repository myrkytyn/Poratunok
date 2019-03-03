package com.example.maks.poratunok;

import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    //оголошую кнопку і текст
    TextView tvText;
    ImageButton btnHelp;
    TextView tvText2;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Пошук елеменів
        tvText = (TextView) findViewById(R.id.tvText);
        tvText2 = (TextView) findViewById(R.id.tvText2);
        btnHelp = (ImageButton) findViewById(R.id.btnHelp);

        //опрацьовувач натискання основної кнопки
        View.OnClickListener oclBtnHelp = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Змінюю текст у tvText
                tvText.setVisibility(View.INVISIBLE);
                tvText2.setVisibility(View.VISIBLE);
            }
        };
        // присвоюю опрацьовувач кнопці
        btnHelp.setOnClickListener(oclBtnHelp);
    }
}
