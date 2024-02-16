package com.example.vetvisor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Screen3 extends AppCompatActivity {

    Button next2;
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

    }
    public void openScreen3(){
        Intent intent = new Intent(this, Screen4.class);
        startActivity(intent);
    }
    public void previous(View view){
        Intent previous = new Intent(this,Screen2.class);
        startActivity(previous);

}}