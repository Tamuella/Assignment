package com.example.assignment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.Objects;

public class OrderSuccessActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_success);
        Objects.requireNonNull(getSupportActionBar()).hide();

        TextView tvHome = findViewById(R.id.tvHome);
        TextView tvHistory = findViewById(R.id.tvHistory);

        tvHome.setOnClickListener(v -> {
            goToHomeScreen();
        });

        tvHistory.setOnClickListener(v -> {
            goToOrderHistory();
        });
    }

    private void goToHomeScreen() {
        Intent intent = new Intent(this, HomePageActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    private void goToOrderHistory() {
        Intent intent = new Intent(this, HomePageActivity.class);
        intent.putExtra("navigation", "goToOrderHistory");
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}