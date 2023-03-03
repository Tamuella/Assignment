package com.example.assignment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {
EditText etUsername, etPassword;
Button btnSignIn;
TextView tvRegister, tvForgotPassword;
DatabaseHandler DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Login");

        etUsername = (EditText) findViewById(R.id.etUsername);
        etPassword = (EditText) findViewById(R.id.etPassword);
        tvRegister = (TextView) findViewById(R.id.tvRegister);
        tvForgotPassword = (TextView) findViewById(R.id.tvForgotPassword);
        btnSignIn = (Button) findViewById(R.id.btnSignIn);
        DB = new DatabaseHandler(this);

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();
                if(username.equals("") || password.equals(""))
                    Toast.makeText(MainActivity.this, "Please enter!", Toast.LENGTH_LONG ).show();
                if (DB.checkUsernamePassword(username, password)){
                    storeUsernameToSharePref(username);
                    Toast.makeText(MainActivity.this, "Correct Password", Toast.LENGTH_LONG).show();
                    Intent myIntent = new Intent(MainActivity.this, HomePageActivity.class);
                    startActivity(myIntent);
                } else {
                    Toast.makeText(MainActivity.this, "Username or Password not correct!", Toast.LENGTH_LONG ).show();
                }
            }
        });

        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(myIntent);
            }
        });

        tvForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(MainActivity.this, ImportProductActivity.class);
                startActivity(myIntent);
            }
        });

    }

    private void storeUsernameToSharePref(String username) {
        SharedPreferences prefs = getSharedPreferences(getString(R.string.preference_root), MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(getString(R.string.preference_username), username);
        editor.apply(); // This line is IMPORTANT. If you miss this one its not gonna work!
    }
}