package com.example.vetvisor;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Pug extends AppCompatActivity {

    private Button characteristicsButton;
    private TextView characteristicsTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pug);

        characteristicsButton = findViewById(R.id.characteristicsButton);
        characteristicsTextView = findViewById(R.id.characteristicsTextView);

        characteristicsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleCharacteristics();
            }
        });
    }

    private void toggleCharacteristics() {
        if (characteristicsTextView.getVisibility() == View.VISIBLE) {
            characteristicsTextView.setVisibility(View.GONE);
        } else {
            characteristicsTextView.setVisibility(View.VISIBLE);
        }
    }
}
