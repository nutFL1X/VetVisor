package com.example.vetvisor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import android.widget.ToggleButton;


public class Screen4 extends AppCompatActivity {
    Button next3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen4);
        next3 = findViewById(R.id.next3);
        next3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openScreen3();
            }
        });

    }
    public void openScreen3(){
        Intent intent = new Intent(this, Screen5.class);
        startActivity(intent);
    }
    public void previous(View view){
        Intent previous = new Intent(this,Screen3.class);
        startActivity(previous);

    }}
