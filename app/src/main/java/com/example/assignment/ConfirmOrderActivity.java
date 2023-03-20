package com.example.assignment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Objects;
import java.util.function.Consumer;

public class ConfirmOrderActivity extends AppCompatActivity {

    ArrayList<Product> listProduct;
    ArrayList<Integer> listQuantity;
    DatabaseHandler DB;
    User user;
    double shipFees = 15000.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_order);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Confirm Order");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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
        rvConfirmOrder.setNestedScrollingEnabled(false);

        Button btnBuy = (Button) findViewById(R.id.btnBuy);
        btnBuy.setOnClickListener(v -> {
            for (int i = 0; i < listProduct.size(); i++) {
                Product product = listProduct.get(i);

                int currentProductQuantity = DB.getProductQuantity(product.getProductID());
                int buyQuantity = listQuantity.get(i);
                String finalQuantity = String.valueOf(currentProductQuantity - buyQuantity);

                DB.updateProductQuantity(product.getProductID(), finalQuantity);
                DB.deleteCartData(product.getProductID());
                DB.insertOrderHistoryData(product, buyQuantity, user.getUsername());
            }

            Intent intent = new Intent(ConfirmOrderActivity.this, OrderSuccessActivity.class);
            startActivity(intent);
        });


        TextView tvUserName = (TextView) findViewById(R.id.tvUserName);
        TextView tvEmail = (TextView) findViewById(R.id.tvEmail);
        TextView tvAdress = (TextView) findViewById(R.id.tvAdress);
        TextView tvPhoneNum = (TextView) findViewById(R.id.tvPhoneNum);
        TextView tvTotalPrice = (TextView) findViewById(R.id.tvTotalPrice);

        if (user != null) {
            tvUserName.setText("Username: " + user.getUsername());
            tvEmail.setText("Email: " + user.getEmail());
            tvAdress.setText("Address: " + user.getAddress());
            tvPhoneNum.setText("Phone number: " + user.getPhoneNum());
        }

        double totalPrice = 0.0;
        for (int i = 0; i < listProduct.size(); i++) {
            double price = Double.parseDouble(listProduct.get(i).getProductPrice());
            int quantity = listQuantity.get(i);
            totalPrice += (price * quantity);
        }

        shipFees = shipFees * listProduct.size();

        totalPrice += shipFees;
        tvTotalPrice.setText("Total Price: " + totalPrice + "đ");
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