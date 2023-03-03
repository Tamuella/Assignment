package com.example.assignment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Objects;

public class ConfirmOrderActivity extends AppCompatActivity {

    ArrayList<Product> listProduct;
    ArrayList<Integer> listQuantity;
    DatabaseHandler DB;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_order);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Confirm Order");

        initData();
        initView();
    }

    private void initData() {
        DB = new DatabaseHandler(this);

        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();

        SharedPreferences prefs = getSharedPreferences(getString(R.string.preference_root), MODE_PRIVATE);
        String username = prefs.getString(getString(R.string.preference_username), "");
        if (!username.isEmpty()) {
            user = DB.getUser(username);
        }

        listProduct = (ArrayList<Product>) bundle.getSerializable("products");
        listQuantity = bundle.getIntegerArrayList("quantity");
    }

    private void initView() {
        ConfirmOrderAdapter adapter = new ConfirmOrderAdapter(this, listProduct, listQuantity);

        RecyclerView rvConfirmOrder = (RecyclerView) findViewById(R.id.rvConfirmOrder);
        LinearLayoutManager lmOrder = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvConfirmOrder.setLayoutManager(lmOrder);
        rvConfirmOrder.setAdapter(adapter);

        Button btnBuy = (Button) findViewById(R.id.btnBuy);
        btnBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                for (int i = 0; i < listProduct.size(); i++) {
                    Product product = listProduct.get(i);

                    int currentProductQuantity = DB.getProductQuantity(product.getProductID());
                    int buyQuantity = listQuantity.get(i);
                    String finalQuantity = String.valueOf(currentProductQuantity - buyQuantity);

                    DB.updateProductQuantity(product.getProductID(), finalQuantity);
                    DB.deleteCartData(product.getProductID());
                }

                Intent intent = new Intent(ConfirmOrderActivity.this, HomePageActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });


        TextView tvUserName = (TextView) findViewById(R.id.tvUserName);
        TextView tvEmail = (TextView) findViewById(R.id.tvEmail);
        TextView tvAdress = (TextView) findViewById(R.id.tvAdress);
        TextView tvPhoneNum = (TextView) findViewById(R.id.tvPhoneNum);

        if (user != null) {
            tvUserName.setText(user.getUsername());
            tvEmail.setText(user.getEmail());
            tvAdress.setText(user.getAddress());
            tvPhoneNum.setText(user.getPhoneNum());
        }
    }
}