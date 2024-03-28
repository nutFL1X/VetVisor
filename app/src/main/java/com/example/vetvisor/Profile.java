package com.example.vetvisor;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import android.app.AlertDialog;
import android.content.DialogInterface;

// Your existing imports...

public class Profile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // Get the data passed from the previous activity
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            // Extract the pet name and image resource ID
            String petName = extras.getString("petName");
            int petImageResId = extras.getInt("petImageResId");

            // Set the pet name and image in the profile activity
            TextView petNameTextView = findViewById(R.id.petNameTextView);
            ImageView petImageView = findViewById(R.id.petImageView);

            petNameTextView.setText(petName);
            petImageView.setImageResource(petImageResId);
        }
    }

    // Method to show the info popup
    public void showInfoPopup(View view) {
        // Get the pet information
        TextView petNameTextView = findViewById(R.id.petNameTextView);
        String petName = "(Need to implement database)";
        String petType = "Dog";
        String petBreed = petNameTextView.getText().toString();
        String lastLocation = "Not implemented yet";

        // Create the info message
        StringBuilder infoMessage = new StringBuilder();
        infoMessage.append("Name of your pet: ").append(petName).append("\n")
                .append("Type of pet: ").append(petType).append("\n")
                .append("Breed of pet: ").append(petBreed).append("\n")
                .append("Last location: ").append(lastLocation);

        // Create and show the alert dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Pet Information");
        builder.setMessage(infoMessage.toString());
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Do nothing or handle any additional actions if needed
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    // Method to open the Symptoms activity
    public void openSymptomsActivity(View view) {
        Intent intent = new Intent(this, Symptoms.class);
        startActivity(intent);
    }
}
