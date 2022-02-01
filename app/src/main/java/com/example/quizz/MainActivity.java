package com.example.quizz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    // DECLARING VARIABLE
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // ADDING BUTTON FUNCTIONALITY
        button = findViewById(R.id.class_6_btn);
        button.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), SubCategoryActivity.class);
            startActivity(intent);
        });
    }
}