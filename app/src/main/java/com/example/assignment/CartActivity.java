package com.example.assignment;

import android.content.Intent;
import android.os.Bundle;
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

        btnBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                ArrayList<Product> listProductChecked = new ArrayList<>();
                for (int i = 0; i < listProduct.size(); i++) {
                    if (adapter.listCheckedItem.get(i)) {
                        listProductChecked.add(listProduct.get(i));
                    }
                }

                if (listProductChecked.size() > 0) {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("products", listProductChecked);
                    bundle.putIntegerArrayList("quantity", listQuantity);

                    Intent intent = new Intent(CartActivity.this, ConfirmOrderActivity.class);
                    intent.putExtras(bundle);
                    startActivity(intent);
                } else {
                    Toast.makeText(CartActivity.this, "Not choose Product yet", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }



}