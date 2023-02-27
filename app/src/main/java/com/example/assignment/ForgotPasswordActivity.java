package com.example.assignment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ForgotPasswordActivity extends AppCompatActivity {
    EditText etUsername, etEmail;
    TextView tvAlreadyHaveAccount;
    Button btnSubmit;
    DatabaseHandler DB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        etUsername = (EditText) findViewById(R.id.etUsername);
        etEmail = (EditText) findViewById(R.id.etEmail);
        tvAlreadyHaveAccount = (TextView) findViewById(R.id.tvAlreadyHaveAccount);
        btnSubmit = (Button) findViewById(R.id.btnSubmit);
        DB = new DatabaseHandler(this);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = etUsername.getText().toString();
                String email = etEmail.getText().toString();
                if(username.equals("") || email.equals(""))
                    Toast.makeText(ForgotPasswordActivity.this, "Please enter!", Toast.LENGTH_LONG ).show();
                if (DB.checkUsernameEmail(username, email)){
                        Toast.makeText(ForgotPasswordActivity.this, "Correct Email", Toast.LENGTH_LONG).show();
                        Intent myIntent = new Intent(ForgotPasswordActivity.this, ChangePasswordActivity.class);
                        myIntent.putExtra("username", username);
                        startActivity(myIntent);
                } else {
                    Toast.makeText(ForgotPasswordActivity.this, "Username or Email not correct!", Toast.LENGTH_LONG ).show();
                }
            }
        });

        tvAlreadyHaveAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(ForgotPasswordActivity.this, MainActivity.class);
                startActivity(myIntent);
            }
        });

    }
}