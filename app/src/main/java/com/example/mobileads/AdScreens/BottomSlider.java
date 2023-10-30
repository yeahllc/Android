package com.example.mobileads.AdScreens;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mobileads.MainActivity;
import com.example.mobileads.R;
import com.example.mobileads.YeahAdsManager;

public class BottomSlider extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_slider);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                YeahAdsManager.showBottomSlider(BottomSlider.this);
            }
        }, 1500);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(BottomSlider.this, MainActivity.class));
        finish();
    }
}