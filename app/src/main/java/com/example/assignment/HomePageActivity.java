package com.example.assignment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Objects;

public class HomePageActivity extends ShowAdsActivity {

    ArrayList<Product> listProduct;
    ArrayList<Integer> listImage = new ArrayList<Integer>();
    DatabaseHandler DB;

    LinearLayout llMyCart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Home Page");

        initImage();
        initData();
        initView();
    }

    private void initData() {
        DB = new DatabaseHandler(this);
        listProduct = DB.readProducts();
        covertData();
    }

    private void covertData() {
        for (int i = 0; i < listProduct.size(); i++) {
            int temp = listProduct.get(i).getImageDrawable();
            int imageDrawable = listImage.get(temp);
            listProduct.get(i).setImageDrawable(imageDrawable);
        }
    }

    private void initImage() {
        listImage.add(R.drawable.thuoc_cam);
        listImage.add(R.drawable.thuoc_cam2);
        listImage.add(R.drawable.thuoc_daday);
        listImage.add(R.drawable.thuoc_ho);
        listImage.add(R.drawable.thuoc_ho2);
    }

    private void initView() {
        HomePageViewAdapter adapter = new HomePageViewAdapter(this, listProduct);
        LinearLayout llMyCart = (LinearLayout) findViewById(R.id.llMyCart);
        LinearLayout llMyOrder = (LinearLayout) findViewById(R.id.llMyOrder);
        LinearLayout llMyMaps = (LinearLayout) findViewById(R.id.llMyMaps);

        RecyclerView rvThuoc = (RecyclerView) findViewById(R.id.rvThuoc);
        LinearLayoutManager lmThuoc = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rvThuoc.setLayoutManager(lmThuoc);
        rvThuoc.setAdapter(adapter);

        RecyclerView rvThuocChucNang = (RecyclerView) findViewById(R.id.rvThuocChucNang);
        LinearLayoutManager lmThuocChucNang = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rvThuocChucNang.setLayoutManager(lmThuocChucNang);
        rvThuocChucNang.setAdapter(adapter);


        llMyCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent intent = new Intent(HomePageActivity.this, CartActivity.class);
                intent.putIntegerArrayListExtra("quantity", adapter.listQuantity);
                startActivity(intent);
            }
        });

        llMyOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent intent = new Intent(HomePageActivity.this, OrderHistoryActivity.class);
                startActivity(intent);
            }
        });

        llMyMaps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Creates an Intent that will load a map of FPT University
//                Uri gmmIntentUri = Uri.parse("geo:10.841285586243103, 106.81054820008802?z=17");
                Uri gmmIntentUri = Uri.parse("https://goo.gl/maps/EZU1SiQrB7rTRVHFA");
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);
            }
        });

        Button importButton = findViewById(R.id.import_button);
        importButton.setOnClickListener(l -> {
            Intent importIntent = new Intent(HomePageActivity.this, ImportProductActivity.class);
            startActivity(importIntent);
        });

        FloatingActionButton fabMessage = findViewById(R.id.fabMessage);
        fabMessage.setOnClickListener(l -> {
            String number = "0799832535";
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.fromParts("sms", number, null)));
        });
    }

}