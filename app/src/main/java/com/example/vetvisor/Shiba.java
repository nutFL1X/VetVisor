package com.example.vetvisor;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class Shiba extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shiba);

        Button characteristicsButton = findViewById(R.id.characteristicsButton);

        characteristicsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an AlertDialog to show the characteristics
                AlertDialog.Builder builder = new AlertDialog.Builder(Shiba.this);
                builder.setTitle("Shiba Inu Characteristics");
                builder.setMessage(getString(R.string.shiba_characteristics));
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.show();
            }
        });
    }
}
