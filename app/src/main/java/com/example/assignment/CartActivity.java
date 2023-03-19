package com.example.assignment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Objects;

public class CartActivity extends AppCompatActivity {

    ArrayList<Product> listProduct;
    ArrayList<Integer> listQuantity;
    DatabaseHandler DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Cart");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initData();
        initView();
    }

    private void initData() {
        DB = new DatabaseHandler(this);
        listProduct = DB.readCart();
        listQuantity = getIntent().getIntegerArrayListExtra("quantity");
    }

    private void initView() {
        CartAdapter adapter = new CartAdapter(this, listProduct, listQuantity);
        Button btnBuy = (Button) findViewById(R.id.btnBuy);

        RecyclerView rvCart = (RecyclerView) findViewById(R.id.rvCart);
        LinearLayoutManager lmCart = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvCart.setLayoutManager(lmCart);
        rvCart.setAdapter(adapter);

        btnBuy.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putSerializable("products", listProduct);
            bundle.putIntegerArrayList("quantity", listQuantity);

            Intent intent = new Intent(CartActivity.this, ConfirmOrderActivity.class);
            intent.putExtras(bundle);
            startActivity(intent);
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