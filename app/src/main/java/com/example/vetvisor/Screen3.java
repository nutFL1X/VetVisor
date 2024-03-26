package com.example.vetvisor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class Screen3 extends AppCompatActivity {

    Button next2;

    private ImageView dog1;
    private ImageView cat1;

    private boolean isDogImage1 = true;
    private boolean isCatImage3 = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen3);

        next2 = findViewById(R.id.next2);
        next2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openScreen3();
            }
        });

        dog1 = findViewById(R.id.dog1);
        cat1 = findViewById(R.id.cat1);

        dog1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeDogImage();
            }
        });

        cat1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeCatImage();
            }
        });
    }

    public void openScreen3() {
        Intent intent = new Intent(this, Screen4.class);
        startActivity(intent);
    }

    public void previous(View view) {
        Intent previous = new Intent(this, Screen2.class);
        startActivity(previous);
    }

    private void changeDogImage() {
        if (isDogImage1) {
            dog1.setImageResource(R.drawable.image2);
            Toast.makeText(this, "Selected Dog", Toast.LENGTH_SHORT).show();
        } else {
            dog1.setImageResource(R.drawable.image1);
        }
        isDogImage1 = !isDogImage1;
        if (!isDogImage1) {
            cat1.setImageResource(R.drawable.image3);
            isCatImage3 = true;
        }
    }

    private void changeCatImage() {
        if (isCatImage3) {
            cat1.setImageResource(R.drawable.image4);
            Toast.makeText(this, "Selected Cat", Toast.LENGTH_SHORT).show();
        } else {
            cat1.setImageResource(R.drawable.image3);
        }
        isCatImage3 = !isCatImage3;
        if (!isCatImage3) {
            dog1.setImageResource(R.drawable.image1);
            isDogImage1 = true;
        }
    }
}
