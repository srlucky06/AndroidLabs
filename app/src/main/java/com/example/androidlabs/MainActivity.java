package com.example.androidlabs;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {


    SharedPreferences pref = null;
    EditText typeField;
    private String stringToSave;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        typeField = findViewById(R.id.typeEmail);
        Button saveButton = findViewById(R.id.loginBtn);

        pref = getSharedPreferences("FileName", Context.MODE_PRIVATE);
        String savedString = pref.getString("ReserveName", " ");//???
        typeField.setText(savedString);

        Intent goToProfile = new Intent(this, ProfileActivity.class);

        saveButton.setOnClickListener(click ->
        {
            goToProfile.putExtra("emailTyped", typeField.getText().toString());
            startActivity(goToProfile);
        });
    }

    private void saveSharedPrefs(String stringToSave) {
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("ReserveName", stringToSave);
        editor.commit();
    }
    @Override
    protected void onPause() {
        super.onPause();
        stringToSave = typeField.getText().toString();
        saveSharedPrefs(stringToSave);

    }
}
