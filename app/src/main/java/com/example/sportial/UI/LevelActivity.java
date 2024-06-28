package com.example.sportial.UI;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sportial.R;

public class LevelActivity extends AppCompatActivity {
    String[] level = {"Beginner", "Intermediate", "Advanced"};
    AutoCompleteTextView autoCompleteTextView;
    ArrayAdapter<String> adapterLevel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level);
        autoCompleteTextView = findViewById(R.id.autoCompleteText);
        adapterLevel = new ArrayAdapter<String>(this, R.layout.list_items, level);
        autoCompleteTextView.setAdapter(adapterLevel);
        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                String selectedLevel = adapterView.getItemAtPosition(position).toString();
                Toast.makeText(LevelActivity.this,"Level"+level,Toast.LENGTH_SHORT).show();
                // Handle the selected level here

            }
        });
    }
}