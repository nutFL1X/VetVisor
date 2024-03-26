package com.example.vetvisor;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class Golden extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_golden);

        Button characteristicsButton = findViewById(R.id.characteristicsButton);

        characteristicsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the CharacteristicsActivity
                Intent intent = new Intent(Golden.this, Profile.class);
                startActivity(intent);
            }
        });
    }
}
