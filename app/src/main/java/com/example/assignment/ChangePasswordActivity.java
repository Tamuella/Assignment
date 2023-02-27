package com.example.assignment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ChangePasswordActivity extends AppCompatActivity {

    EditText etPassword, etConfirmPassword;
    Button btnSubmit;
    DatabaseHandler DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        etPassword = (EditText) findViewById(R.id.etPassword);
        etConfirmPassword = (EditText) findViewById(R.id.etConfirmPassword);
        btnSubmit = (Button) findViewById(R.id.btnSubmit);
        DB = new DatabaseHandler(this);
        String username = getIntent().getStringExtra("username");

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String password = etPassword.getText().toString();
                String confirmPassword = etConfirmPassword.getText().toString();
                if(password.equals("") || confirmPassword.equals("")) {
                    Toast.makeText(ChangePasswordActivity.this, "Please enter!", Toast.LENGTH_LONG).show();
                    return;
                }
                if(!password.equals(confirmPassword)) {
                    Toast.makeText(ChangePasswordActivity.this, "Password not same with Confirm Password!", Toast.LENGTH_LONG).show();
                }else{
                    DB.updateUserDB(username, password);
                    Toast.makeText(ChangePasswordActivity.this, "Change Password Successfully!!", Toast.LENGTH_LONG ).show();
                    Intent myIntent = new Intent(ChangePasswordActivity.this, MainActivity.class);
                    myIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(myIntent);
                }
            }
        });
    }
}