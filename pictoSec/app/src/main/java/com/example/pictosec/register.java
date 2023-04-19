package com.example.pictosec;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.navigation.ui.AppBarConfiguration;

import com.example.pictosec.databinding.ActivityRegisterBinding;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.lang.String;

public class register extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityRegisterBinding binding;

    private Button register;

    private Button rLogin;
    private databaseManagement db;

    private EditText userIDPrompt;

    private EditText passwordPrompt;

    private EditText passwordConfirm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        db = new databaseManagement();
        userIDPrompt = (EditText) findViewById(R.id.inputUser);
        passwordPrompt = (EditText) findViewById(R.id.inputPass);
        passwordConfirm = (EditText) findViewById(R.id.inputPass2);
        register = (Button) findViewById(R.id.registerB);
        rLogin = (Button) findViewById(R.id.back);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userID = userIDPrompt.getText().toString();
                String password = passwordPrompt.getText().toString();
                String passwordC = passwordConfirm.getText().toString();
                if(userID.isEmpty() || password.isEmpty() || passwordC.isEmpty()) {
                    Toast.makeText(register.this, "Make sure no fields are empty!", Toast.LENGTH_SHORT).show();
                }else if(password.compareTo(passwordC) != 0) {
                    Toast.makeText(register.this, "Make sure passwords match!", Toast.LENGTH_SHORT).show();
                }else if(databaseManagement.getUserQuery(userID)) {
                    Toast.makeText(register.this, "Username is taken!", Toast.LENGTH_SHORT).show();
                }else{
                    databaseManagement.register(userID, password);
                    finish();
                }
            }
        });
        rLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        return;
    }
}