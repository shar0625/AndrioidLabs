package com.cst2335.shar0625;


import android.content.SharedPreferences;
import android.widget.EditText;
import android.util.Log;
import android.content.Intent;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    private EditText email_address,password;
    public static final String TAG = "PROFILE_ACTIVITY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Getting email address and password from the user by its id.
        email_address = findViewById(R.id.enter_email);
        password = findViewById(R.id.password);

        final Button loginButton = findViewById(R.id.button_login);
        loginButton.setOnClickListener(view -> {
            Intent goToProfile = new Intent(MainActivity.this, ProfileActivity.class);
            goToProfile.putExtra("EMAIL", password.getText().toString());

            Log.e(TAG, "email" + password.getText().toString());

            startActivity(goToProfile);
        });
    }
    @Override
    protected void onResume() {
        super.onResume();

        SharedPreferences sharedpref = getSharedPreferences("MySharedPref", MODE_PRIVATE);

        String s1 = sharedpref.getString("name", "");
        int a = sharedpref.getInt("email", 0);

        email_address.setText(s1);
        password.setText(String.valueOf(a));
    }
    @Override
    protected void onPause() {
        super.onPause();

        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sharedPreferences.edit();

        myEdit.putString("name", email_address.getText().toString());
        myEdit.putInt("email", Integer.parseInt(password.getText().toString()));
        myEdit.apply();
    }
    ;}