package com.example.androidlabs;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;


public class ProfileActivity extends AppCompatActivity {

    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final String ACTIVITY_NAME = "PROFILE_ACTIVITY";
    private ImageButton mImageButton;
    private EditText typeField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e(ACTIVITY_NAME,"In function : " + "onCreate");

        setContentView(R.layout.activity_profile);

        Intent fromMain = getIntent();
        String typed =fromMain.getStringExtra("emailTyped");

        EditText emailAddress = findViewById(R.id.enterEmail);
        emailAddress.setText(typed);

        mImageButton =(ImageButton)findViewById(R.id.imageBtn);
        mImageButton.setOnClickListener(bt -> dispatchTakePictureIntent());

        Button cbutton = findViewById(R.id.goToChat);
        Intent goToChat =new Intent(this, ChatRoomActivity.class);
        cbutton.setOnClickListener(Click -> startActivity(goToChat));

    }
    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
       super.onActivityResult(requestCode, resultCode, data);
        Log.e(ACTIVITY_NAME,"In function : " + "onActivityResult");

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            mImageButton.setImageBitmap(imageBitmap);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e(ACTIVITY_NAME,"In function : " + "onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e(ACTIVITY_NAME,"In function : " + "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e(ACTIVITY_NAME,"In function : " + "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e(ACTIVITY_NAME,"In function : " + "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e(ACTIVITY_NAME,"In function : " + "onDestroy");
    }


}
