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
                if (isDogImage1 && isCatImage3) {
                    Toast.makeText(Screen3.this, "Select an animal", Toast.LENGTH_SHORT).show();
                } else if (isDogImage1) {
                    openScreen5();
                } else if (isCatImage3) {
                    openScreen4();
                }
            }
        });

        dog1 = findViewById(R.id.dog1);
        cat1 = findViewById(R.id.cat1);

        dog1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isDogImage1) {
                    selectDogImage();
                }
            }
        });

        cat1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isCatImage3) {
                    selectCatImage();
                }
            }
        });
    }

    public void openScreen4() {
        Intent intent = new Intent(this, Screen4.class);
        startActivity(intent);
    }

    public void openScreen5() {
        Intent intent = new Intent(this, Screen5.class);
        startActivity(intent);
    }

    public void previous(View view) {
        Intent previous = new Intent(this, Screen2.class);
        startActivity(previous);
    }

    private void selectDogImage() {
        dog1.setImageResource(R.drawable.image2);
        cat1.setImageResource(R.drawable.image3);

        isDogImage1 = false;
        isCatImage3 = true;

        Toast.makeText(this, "Selected Dog", Toast.LENGTH_SHORT).show();
    }

    private void selectCatImage() {
        dog1.setImageResource(R.drawable.image1);
        cat1.setImageResource(R.drawable.image4);

        isDogImage1 = true;
        isCatImage3 = false;

        Toast.makeText(this, "Selected Cat", Toast.LENGTH_SHORT).show();
    }
}
