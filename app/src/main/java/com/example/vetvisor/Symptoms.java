package com.example.vetvisor;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class Symptoms extends AppCompatActivity {

    // Symptoms checkboxes
    private CheckBox checkboxVomiting, checkboxWeightLoss, checkboxReducedAppetite,
            checkboxEarInfections, checkboxHairLoss, checkboxShivering,
            checkboxTroubleBreathing, checkboxDifficultyUrinating, checkboxFever,
            checkboxHighTemperature;

    // Button to show possible diseases
    private Button buttonShowDiseases;

    // TextView to display possible diseases
    private TextView textviewPossibleDiseases;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_symptoms);

        // Initialize UI components
        initUI();

        // Set OnClickListener for the button
        buttonShowDiseases.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get selected symptoms
                List<String> selectedSymptoms = getSelectedSymptoms();

                // Generate possible diseases based on selected symptoms
                List<String> possibleDiseases = generatePossibleDiseases(selectedSymptoms);

                // Display possible diseases
                displayPossibleDiseases(possibleDiseases);
            }
        });
    }

    // Initialize UI components
    private void initUI() {
        checkboxVomiting = findViewById(R.id.checkbox_vomiting);
        checkboxWeightLoss = findViewById(R.id.checkbox_weight_loss);
        checkboxReducedAppetite = findViewById(R.id.checkbox_reduced_appetite);
        checkboxEarInfections = findViewById(R.id.checkbox_ear_infections);
        checkboxHairLoss = findViewById(R.id.checkbox_hair_loss);
        checkboxShivering = findViewById(R.id.checkbox_shivering);
        checkboxTroubleBreathing = findViewById(R.id.checkbox_trouble_breathing);
        checkboxDifficultyUrinating = findViewById(R.id.checkbox_difficulty_urinating);
        checkboxFever = findViewById(R.id.checkbox_fever);
        checkboxHighTemperature = findViewById(R.id.checkbox_high_temperature);

        buttonShowDiseases = findViewById(R.id.button_show_diseases);

        textviewPossibleDiseases = findViewById(R.id.textview_possible_diseases);
    }

    // Get selected symptoms
    private List<String> getSelectedSymptoms() {
        List<String> selectedSymptoms = new ArrayList<>();

        if (checkboxVomiting.isChecked()) selectedSymptoms.add("Vomiting");
        if (checkboxWeightLoss.isChecked()) selectedSymptoms.add("Weight Loss");
        if (checkboxReducedAppetite.isChecked()) selectedSymptoms.add("Reduced Appetite");
        if (checkboxEarInfections.isChecked()) selectedSymptoms.add("Ear Infections");
        if (checkboxHairLoss.isChecked()) selectedSymptoms.add("Hair Loss");
        if (checkboxShivering.isChecked()) selectedSymptoms.add("Shivering");
        if (checkboxTroubleBreathing.isChecked()) selectedSymptoms.add("Trouble Breathing");
        if (checkboxDifficultyUrinating.isChecked()) selectedSymptoms.add("Difficulty Urinating");
        if (checkboxFever.isChecked()) selectedSymptoms.add("Fever");
        if (checkboxHighTemperature.isChecked()) selectedSymptoms.add("High Temperature");

        return selectedSymptoms;
    }

    // Generate possible diseases based on selected symptoms
    private List<String> generatePossibleDiseases(List<String> selectedSymptoms) {
        List<String> possibleDiseases = new ArrayList<>();

        // Add diseases based on symptom combinations
        if (selectedSymptoms.contains("Vomiting") && selectedSymptoms.contains("Weight Loss")
                && selectedSymptoms.contains("Reduced Appetite")) {
            possibleDiseases.add("Gastrointestinal issues");
            possibleDiseases.add("Parasites");
            possibleDiseases.add("Systemic diseases like kidney disease or cancer");
        }
        if (selectedSymptoms.contains("Ear Infections") && selectedSymptoms.contains("Hair Loss")){
            possibleDiseases.add("Ear mites");
            possibleDiseases.add("Other Ear Infections");
            possibleDiseases.add("Allergies or hormonal imbalances");
        }
        if (selectedSymptoms.contains("Shivering") && selectedSymptoms.contains("Trouble Breathing"))
        {
            possibleDiseases.add("Respiratory infections");
            possibleDiseases.add("Pneumonia");
            possibleDiseases.add("Congestive Heart Failure");
        }
        if (selectedSymptoms.contains("Difficulty Urinating") && selectedSymptoms.contains("Fever"))
        {
            possibleDiseases.add("Urinary tract infections");
            possibleDiseases.add("Urinary blockages");
            possibleDiseases.add("Kidney Issues");
        }
        if (selectedSymptoms.contains("High Temperature") && selectedSymptoms.contains("Fever"))
        {
            possibleDiseases.add("Infection");
            possibleDiseases.add("Inflammation");
            possibleDiseases.add("Possibly due to bacterial or viral infections");
        }
        if (selectedSymptoms.contains("Weight Loss") && selectedSymptoms.contains("Reduced Appetite")
                && selectedSymptoms.contains("Hair Loss")) {
            possibleDiseases.add("Endocrine disorders");
            possibleDiseases.add("Hypothyroidism/Cushing disease");
            possibleDiseases.add("Nutritional deficiencies");
        }
        if (selectedSymptoms.contains("Vomiting") && selectedSymptoms.contains("Weight Loss") && selectedSymptoms.contains("Fever"))
        {
            possibleDiseases.add("Systemic issue or infection");
            possibleDiseases.add("Parvovirus");
            possibleDiseases.add("Pancreatitis");
        }
        if (selectedSymptoms.contains("Shivering") && selectedSymptoms.contains("Difficulty Urinating"))
        {
            possibleDiseases.add("Bladder stones");
            possibleDiseases.add("Prostate issues in male animals");
        }
        if (selectedSymptoms.contains("Vomiting") && selectedSymptoms.contains("Ear Infections") && selectedSymptoms.contains("Hair Loss"))
        {
            possibleDiseases.add("Allergies");
            possibleDiseases.add("Ear mites");
            possibleDiseases.add("Skin conditions like mange");
        }
        if (selectedSymptoms.contains("Reduced Appetite") && selectedSymptoms.contains("Trouble Breathing"))
        {
            possibleDiseases.add("Respiratory issues like asthma");
            possibleDiseases.add("Pneumonia");
            possibleDiseases.add("Systemic conditions affecting breathing");
        }
        if (selectedSymptoms.contains("High Temperature") && selectedSymptoms.contains("Difficulty Urinating"))
        {
            possibleDiseases.add("Possible urinary tract infections");
            possibleDiseases.add("Kidney issues");
            possibleDiseases.add("Especially if accompanied by pain or discomfort");
        }
        if (selectedSymptoms.contains("Vomiting") && selectedSymptoms.contains("Weight Loss") && selectedSymptoms.contains("Shivering"))
        {
            possibleDiseases.add("Systemic infections");
            possibleDiseases.add("Gastrointestinal issues");
            possibleDiseases.add("Metabolic disorders");
        }
        if (selectedSymptoms.contains("Fever") && selectedSymptoms.contains("Hair Loss"))
        {
            possibleDiseases.add("May suggest underlying infections");
            possibleDiseases.add("Hormonal imbalances");
            possibleDiseases.add("Skin conditions like ringworm");
        }
        if (selectedSymptoms.contains("Difficulty Urinating") && selectedSymptoms.contains("Hair loss")) {
            possibleDiseases.add("Could indicate urinary tract obstructions");
            possibleDiseases.add("Bladder infections");
            possibleDiseases.add("Skin conditions affecting the genital area");
        }
        if (selectedSymptoms.contains("Reduced Appetite") && selectedSymptoms.contains("Fever") && selectedSymptoms.contains("Trouble Breathing")) {
            possibleDiseases.add("Systemic infection");
            possibleDiseases.add("Disease affecting multiple organ systems");
            possibleDiseases.add("Potentially serious and requiring immediate veterinary attention");
        }
        if (selectedSymptoms.contains("Vomiting") && selectedSymptoms.contains("Ear Infections") && selectedSymptoms.contains("Shivering")) {
            possibleDiseases.add("Indicate a combination of gastrointestinal issues");
            possibleDiseases.add("Infections affecting the ears or respiratory system");
        }
        if (selectedSymptoms.contains("Weight Loss") && selectedSymptoms.contains("Shivering") && selectedSymptoms.contains("Fever")) {
            possibleDiseases.add("May suggest systemic infections");
            possibleDiseases.add("Autoimmune diseases");
            possibleDiseases.add("Metabolic disorders");
        }
        if (selectedSymptoms.contains("Vomiting") && selectedSymptoms.contains("Hair Loss") && selectedSymptoms.contains("Difficulty Urinating")) {
            possibleDiseases.add("Could suggest a combination of gastrointestinal issues");
            possibleDiseases.add("Skin conditions");
            possibleDiseases.add("Urinary tract problems");
        }
        if (selectedSymptoms.contains("Reduced Appetite") && selectedSymptoms.contains("Shivering") && selectedSymptoms.contains("Trouble Breathing")) {
            possibleDiseases.add("Serious systemic issue affecting multiple organ systems");
            possibleDiseases.add("Possibly indicating severe infections");
            possibleDiseases.add("Organ failure");
        }
        if (selectedSymptoms.contains("Weight Loss") && selectedSymptoms.contains("Hair Loss") && selectedSymptoms.contains("Fever")) {
            possibleDiseases.add("Serious systemic conditions affecting metabolism");
            possibleDiseases.add("Hormonal imbalances");
            possibleDiseases.add("Severe infections");
        }
        if (selectedSymptoms.contains("Weight Loss") && selectedSymptoms.contains("Ear Infections") && selectedSymptoms.contains("Fever")) {
            possibleDiseases.add("Ear infections");
            possibleDiseases.add("Respiratory infections");
            possibleDiseases.add("Systemic infections");
        }
        if (selectedSymptoms.contains("Trouble Breathing") && selectedSymptoms.contains("High Temperature")) {
            possibleDiseases.add("Respiratory infections");
            possibleDiseases.add("Pneumonia");
            possibleDiseases.add("Systemic infections");
        }
        if (selectedSymptoms.contains("Vomiting") && selectedSymptoms.contains("Trouble Breathing") && selectedSymptoms.contains("Fever")) {
            possibleDiseases.add("Respiratory infections");
            possibleDiseases.add("Gastrointestinal issues");
            possibleDiseases.add("Systemic infections");
        }
        return possibleDiseases;
    }

    // Display possible diseases
    private void displayPossibleDiseases(List<String> possibleDiseases) {
        StringBuilder diseasesText = new StringBuilder("Possible Diseases:\n");
        for (String disease : possibleDiseases) {
            diseasesText.append("- ").append(disease).append("\n");
        }
        textviewPossibleDiseases.setText(diseasesText.toString());
    }
}
