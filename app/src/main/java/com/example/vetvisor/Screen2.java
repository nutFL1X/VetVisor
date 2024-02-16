package com.example.vetvisor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Screen2 extends AppCompatActivity {
    Button next1;
    EditText name;

    public static final String PET_NAME = "com.example.vetvisor";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen2);
        next1 = findViewById(R.id.next1);
        next1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openScreen3();
            }
        });

    }
    public void openScreen3(){
        name = findViewById(R.id.nickname);
        Intent intent = new Intent(this, Screen3.class);
        String namePet = name.getText().toString();
        intent.putExtra(PET_NAME,namePet);
        startActivity(intent);
    }
public void previous(View view){
        Intent previous = new Intent(this,MainActivity.class);
        startActivity(previous);
}
}
