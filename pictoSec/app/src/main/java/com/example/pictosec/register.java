package com.example.pictosec;

import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.pictosec.databinding.ActivityRegisterBinding;

public class register extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityRegisterBinding binding;

    private Button registerButton;

    private databaseManagement db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        db = new databaseManagement();

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userID = userIDPrompt.getText().toString();
                String password = passwordPrompt.getText().toString();

                if(userID.isEmpty() || password.isEmpty()) {
                    Toast.makeText(Login.this, "Make sure no fields are empty!", Toast.LENGTH_SHORT).show();
                } else {
                    if(userID.equals("testUser") && password.equals("12345")) {
                        finish();
                    } else {
                        Toast.makeText(Login.this, "Invalid Credentials!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        return;
    }
}