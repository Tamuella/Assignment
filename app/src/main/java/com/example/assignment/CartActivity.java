package com.example.assignment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

public class CartActivity extends AppCompatActivity {

    ArrayList<Product> listProduct;
    DatabaseHandler DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        initData();
        initView();
    }

    private void initData() {
        DB = new DatabaseHandler(this);
        listProduct = DB.readCart();
    }

    private void initView() {
        CartAdapter adapter = new CartAdapter(this, listProduct);

        RecyclerView rvCart = (RecyclerView) findViewById(R.id.rvCart);
        LinearLayoutManager lmCart = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvCart.setLayoutManager(lmCart);
        rvCart.setAdapter(adapter);
    }
}