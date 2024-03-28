package com.example.vetvisor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Screen2 extends AppCompatActivity {
    Button next1;
    EditText name;

    public static final String PET_NAME = "com.example.vetvisor";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen2);

        // Initialize views
        next1 = findViewById(R.id.next1);

        // Set click listener for "Next" button
        next1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openScreen3();
            }
        });

        // Set click listener for "Need help?" TextView
        TextView needHelpTextView = findViewById(R.id.textView3);
        needHelpTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Show a toast message for 3 seconds
                Toast.makeText(Screen2.this, "Contact @9004129081", Toast.LENGTH_LONG).show();
            }
        });
    }

    // Method to open Screen3
    public void openScreen3(){
        name = findViewById(R.id.nickname);
        Intent intent = new Intent(this, Screen3.class);
        String namePet = name.getText().toString();
        intent.putExtra(PET_NAME,namePet);
        startActivity(intent);
    }

    // Method to navigate back to MainActivity
    public void previous(View view){
        Intent previous = new Intent(this,MainActivity.class);
        startActivity(previous);
    }
}
