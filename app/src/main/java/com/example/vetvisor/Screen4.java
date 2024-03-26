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
    }

    private void openPugActivity() {
        Intent intent = new Intent(this, Pug.class);
        startActivity(intent);
    }
}
