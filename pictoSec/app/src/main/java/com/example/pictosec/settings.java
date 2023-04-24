package com.example.pictosec;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.fragment.NavHostFragment;
import androidx.preference.PreferenceManager;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Scanner;

public class settings extends AppCompatActivity {

    private Button bButton;
    private Button sButton;
    private ToggleButton passStrength;
    private EditText iSet;
    SharedPreferences pref;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        bButton = (Button) findViewById(R.id.button2);
        sButton = (Button) findViewById(R.id.saveB);
        iSet = (EditText) findViewById(R.id.imageSet);
        passStrength = (ToggleButton) findViewById(R.id.pStrength);
        pref = this.getSharedPreferences("SettingsPref", Context.MODE_PRIVATE);
        if(pref.getBoolean("toggle", false)){
            passStrength.setChecked(true);
        }
        bButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        sButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String str = iSet.getText().toString();
                int num;
                if(str.isEmpty()){
                    num = 1;
                }else{
                    num = Integer.parseInt(str);
                }
                if(num != 1 && num != 2 && num != 0) {
                    Toast.makeText(settings.this, "Must Enter 1 or 2!", Toast.LENGTH_SHORT).show();
                }else{
                    editor = pref.edit();
                    editor.putInt("ImageSet", num);
                    editor.putBoolean("toggle", passStrength.isChecked());
                    editor.apply();
                    Toast.makeText(settings.this, "Settings saved!", Toast.LENGTH_SHORT).show();
                    iSet.getText().clear();
                }
            }
        });
    }
}