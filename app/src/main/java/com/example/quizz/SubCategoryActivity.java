package com.example.quizz;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class SubCategoryActivity extends AppCompatActivity {

    // DECLARING VARIABLES
    String collId;
    Spinner subDropdown;

    Button startBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject);


        subDropdown = findViewById(R.id.subjectSpinner);

        startBtn = findViewById(R.id.startBtn);

        // ARRAYS FOR DROPDOWNS
        String[] subjects = new String[]{"Kids", "Gaming", "Sports", "Movies" };


        // SETTING UP ADAPTERS
        ArrayAdapter<String> subAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, subjects);

        subDropdown.setAdapter(subAdapter);
        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),QuestionsActivity.class);
                collId = subDropdown.getSelectedItem().toString();
                Log.d("LOGGER",collId);
                intent.putExtra("topic",collId);
                startActivity(intent);
            }
        });

    }

}