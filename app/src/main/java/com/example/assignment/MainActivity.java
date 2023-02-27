package com.example.assignment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
EditText etUsername, etPassword;
Button btnSignIn;
TextView tvRegister, tvForgotPassword;
DatabaseHandler DB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
}