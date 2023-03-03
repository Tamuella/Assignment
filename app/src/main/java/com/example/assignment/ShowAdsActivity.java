package com.example.assignment;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

public abstract class ShowAdsActivity extends AppCompatActivity {
    Dialog dialog;
    Handler handler = new Handler();
    Runnable runnable;

    final int delay = 10000;



    @Override
    protected void onResume() {
        super.onResume();
        if (dialog == null) {
            initDialog();
        }

        handler.postDelayed( runnable = () -> {
            if (!dialog.isShowing()) dialog.show();
            handler.postDelayed(runnable, delay);
        }, delay);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (dialog.isShowing()) dialog.dismiss();
        handler.removeCallbacks(runnable);
    }

    public void initDialog() {
        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(
                new ColorDrawable(android.graphics.Color.TRANSPARENT));

        ImageView imageView = new ImageView(this);
        imageView.setImageResource(R.drawable.medicine_ads);
        dialog.addContentView(imageView, new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
    }
}
