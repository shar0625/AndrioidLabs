package com.cst2335.shar0625;

import static com.cst2335.shar0625.R.id.toggleButton;

import androidx.appcompat.app.AppCompatActivity;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {
    //LinearLayout mainland;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_linear);
    }
    public void display_toast(View v){

        Toast.makeText(MainActivity.this,getString(R.string.TOAST_MESSAGE)  , Toast.LENGTH_SHORT).show();
    }



    public void display_snack (View v) {

        @SuppressLint("UseSwitchCompatOrMaterialCode") Switch toggle = (Switch) findViewById(toggleButton);

        toggle.setOnCheckedChangeListener((compoundButton, isChecked) -> {
                    Snackbar snack;
                    if (isChecked) {
                        snack = Snackbar.make(toggle, R.string.SWITCH_ON, Snackbar.LENGTH_LONG);

                    }
                    else {
                        snack = Snackbar.make(toggle, R.string.SWITCH_OFF, Snackbar.LENGTH_LONG);

                    }
                    snack.setAction(R.string.undo, click -> compoundButton.setChecked(false));
                    snack.show();

                }
        );
    }
}