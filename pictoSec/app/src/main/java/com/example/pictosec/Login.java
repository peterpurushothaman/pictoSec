package com.example.pictosec;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public class Login extends AppCompatActivity {

    private EditText userIDPrompt;
    private EditText passwordPrompt;
    private Button loginButton;
    private Button rButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userIDPrompt = (EditText) findViewById(R.id.inputUserID);
        passwordPrompt = (EditText) findViewById(R.id.inputPassword);
        loginButton = (Button) findViewById(R.id.loginButton);
        rButton = (Button) findViewById(R.id.registerButton);
         loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userID = userIDPrompt.getText().toString();
                String password = passwordPrompt.getText().toString();
                users.username = userID;
                if(userID.isEmpty() || password.isEmpty()) {
                    Toast.makeText(Login.this, "Make sure no fields are empty!", Toast.LENGTH_SHORT).show();
                } else {
                    try {
                        if(databaseManagement.getLogin(userID, password)) {
                            finish();
                        } else {
                            Toast.makeText(Login.this, "Invalid Credentials!", Toast.LENGTH_SHORT).show();
                        }
                    } catch (UnsupportedEncodingException e) {
                        throw new RuntimeException(e);
                    } catch (NoSuchAlgorithmException e) {
                        throw new RuntimeException(e);
                    } catch (InvalidKeySpecException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });
        rButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this, register.class));
            }
        });

    }

    @Override
    public void onBackPressed() {
        return;
    }
}