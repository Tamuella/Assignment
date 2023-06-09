package com.example.assignment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {
    EditText etUsername, etPassword, etConfirmPassword, etEmail, etAddress, etPhoneNum;
    TextView tvAlreadyHaveAccount, tvForgotPassword;
    Button btnSignUp;
    DatabaseHandler DB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        etUsername = (EditText) findViewById(R.id.etUsername);
        etPassword = (EditText) findViewById(R.id.etPassword);
        etConfirmPassword = (EditText) findViewById(R.id.etConfirmPassword);
        etEmail = (EditText) findViewById(R.id.etEmail);
        etPhoneNum = (EditText) findViewById(R.id.etPhoneNum);
        etAddress = (EditText) findViewById(R.id.etAddress);
        tvAlreadyHaveAccount = (TextView) findViewById(R.id.tvAlreadyHaveAccount);
        tvForgotPassword = (TextView) findViewById(R.id.tvForgotPassword);
        btnSignUp = (Button) findViewById(R.id.btnSignUp);
        DB = new DatabaseHandler(this);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();
                String confirmPassword = etConfirmPassword.getText().toString();
                String address = etAddress.getText().toString();
                String phoneNum = etPhoneNum.getText().toString();
                String email = etEmail.getText().toString();
                if(username.equals("") || password.equals("") || confirmPassword.equals("")|| email.equals("")){
                    Toast.makeText(RegisterActivity.this, "Please enter!", Toast.LENGTH_LONG ).show();
                    return;
                }
                if(!password.equals(confirmPassword)) {
                    Toast.makeText(RegisterActivity.this, "Password not same with Confirm Password!", Toast.LENGTH_LONG).show();
                    return;
                }
                if (!DB.checkUsername(username)){
                    boolean insert = DB.insertUserData(username, password, email, address, phoneNum);
                    if(insert){
                        finish();
                        Toast.makeText(RegisterActivity.this, "Registered successfully", Toast.LENGTH_LONG).show();
                    }
                } else {
                    finish();
                    Toast.makeText(RegisterActivity.this, "Username already exist!!", Toast.LENGTH_LONG ).show();
                }
            }
        });

        tvAlreadyHaveAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(RegisterActivity.this, MainActivity.class);
                startActivity(myIntent);
            }
        });

        tvForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(RegisterActivity.this, ForgotPasswordActivity.class);
                startActivity(myIntent);
            }
        });

    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            onBackPressed(); // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }
}