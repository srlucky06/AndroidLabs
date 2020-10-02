package com.example.androidlabs;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {
    private Button clickHereButton;
    private Switch switchOnOff;
    private View mainContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_linear);
//        setContentView(R.layout.activity_main_grid);
//        setContentView(R.layout.activity_main_relative);

        clickHereButton = findViewById(R.id.clickHereButton);
        //switchOnOff = findViewById(R.id.switchOnOff);
        // mainContainer = findViewById(R.id.mainContainer);

        clickHereButton.setOnClickListener((v) -> {

                    Toast.makeText(MainActivity.this, getResources().getString(R.string.toast_message), Toast.LENGTH_LONG).show();

                }
        );
        switchOnOff = findViewById(R.id.switchOnOff);
        switchOnOff.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {

                    Snackbar.make(switchOnOff, getResources().getString(R.string.switch_on), Snackbar.LENGTH_LONG)
                            .setAction(R.string.undo, v -> {
                                switchOnOff.setChecked(false);
                            })
                            .show();

                } else {
                    Snackbar.make(switchOnOff, getResources().getString(R.string.switch_off), Snackbar.LENGTH_LONG)
                            .setAction(R.string.undo, v -> {
                                switchOnOff.setChecked(true);
                            })
                            .show();
                }
            }
        });


    }
}


