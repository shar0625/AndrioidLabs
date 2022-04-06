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
import android.widget.Button;
import android.widget.ImageButton;

public class ProfileActivity extends AppCompatActivity {
    Button chat_button, goToToolbarBtn,goToWeatherBtn;

    ImageButton button_image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        chat_button = (Button) findViewById(R.id.chat_button);
        button_image= findViewById(R.id.image_button);
        goToToolbarBtn=findViewById(R.id.GoToToolbarPage);
        goToWeatherBtn=findViewById(R.id.GoToWeatherPage);
        button_image.setOnClickListener(view -> dispatchTakePictureIntent());
        chat_button.setOnClickListener(view -> {
            startActivity(new Intent(ProfileActivity.this, ChatRoomActivity.class));
        });
        goToToolbarBtn.setOnClickListener(c -> {
            Intent goToMenuPage = new Intent(ProfileActivity.this, TestToolbar.class);

            startActivity(goToMenuPage);

        });
      goToWeatherBtn.setOnClickListener(c ->{
          Intent weather = new Intent(ProfileActivity.this, WeatherForecast.class);
          startActivity(weather);
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
                        Bitmap img_bitmap = (Bitmap) data.getExtras().get("data");
                        button_image.setImageBitmap(img_bitmap);
                    }
                    else if(result.getResultCode() == Activity.RESULT_CANCELED)
                        Log.e(TAG, "User refused to capture a picture.");
                }
            } );

    }

