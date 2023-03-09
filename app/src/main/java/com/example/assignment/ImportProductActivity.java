package com.example.assignment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ImportProductActivity extends AppCompatActivity {
    EditText etProductID, etProductName, etProductQuantity, etProductPrice, etImageDrawable;
    TextView tvBack;
    Button btnAdd;
    DatabaseHandler DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_import_product);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        etProductID = (EditText) findViewById(R.id.etProductID);
        etProductName = (EditText) findViewById(R.id.etProductName);
        etProductQuantity = (EditText) findViewById(R.id.etProductQuantity);
        etProductPrice = (EditText) findViewById(R.id.etProductPrice);
        etImageDrawable = (EditText) findViewById(R.id.etImageDrawable);
        tvBack = (TextView) findViewById(R.id.tvBack);
        btnAdd = (Button) findViewById(R.id.btnAdd);
        DB = new DatabaseHandler(this);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String productID = etProductID.getText().toString();
                String productName = etProductName.getText().toString();
                String productQuantity = etProductQuantity.getText().toString();
                String productPrice = etProductPrice.getText().toString();
                String imageDrawable = etImageDrawable.getText().toString();
                if(productID.equals("") || productName.equals("") || productQuantity.equals("") || productPrice.equals("")){
                    Toast.makeText(ImportProductActivity.this, "Please enter!", Toast.LENGTH_LONG ).show();
                    return;
                }
                    boolean insert = DB.insertProductData(productID, productName, productQuantity, productPrice, Integer.parseInt(imageDrawable));
                    if(insert){
                        Toast.makeText(ImportProductActivity.this, "Add successfully", Toast.LENGTH_LONG).show();
                    }

            }
        });

        tvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(ImportProductActivity.this, MainActivity.class);
                startActivity(myIntent);
            }
        });


    }


}