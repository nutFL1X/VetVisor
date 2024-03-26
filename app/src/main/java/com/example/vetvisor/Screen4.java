package com.example.vetvisor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class Screen4 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen4);

        ImageView pugImage = findViewById(R.id.pu);
        pugImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPugActivity();
            }
        });

        ImageView shibaImage = findViewById(R.id.shib);
        shibaImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openShibaActivity();
            }
        });

        ImageView goldenImage = findViewById(R.id.gold);
        goldenImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGoldenActivity();
            }
        });

        ImageView bullImage = findViewById(R.id.bull);
        bullImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openBullActivity();
            }
        });
    }

    private void openPugActivity() {
        Intent intent = new Intent(this, Pug.class);
        startActivity(intent);
    }

    private void openShibaActivity() {
        Intent intent = new Intent(this, Shiba.class);
        startActivity(intent);
    }

    private void openGoldenActivity() {
        Intent intent = new Intent(this, Golden.class);
        startActivity(intent);
    }

    private void openBullActivity() {
        Intent intent = new Intent(this, Bull.class);
        startActivity(intent);
    }
}
