package com.cst2335.shar0625;
import static android.content.ContentValues.TAG;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class ProfileActivity extends AppCompatActivity {
    final Button chat_button = (Button) findViewById(R.id.chat_button);

    ImageButton button_image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        button_image= findViewById(R.id.image_button);
        button_image.setOnClickListener(view -> dispatchTakePictureIntent());
        chat_button.setOnClickListener(view -> {
            startActivity(new Intent(ProfileActivity.this, ChatRoomActivity.class));
        });

    }
    @SuppressLint("QueryPermissionsNeeded")
    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            myPictureTakerLauncher.launch(takePictureIntent);
        }
    }
    ActivityResultLauncher<Intent> myPictureTakerLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult()
            ,new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        assert data != null;
                        Bitmap imgbitmap = (Bitmap) data.getExtras().get("data");
                        button_image.setImageBitmap(imgbitmap);
                    }
                    else if(result.getResultCode() == Activity.RESULT_CANCELED)
                        Log.e(TAG, "User refused to capture a picture.");
                }
            } );



    }

