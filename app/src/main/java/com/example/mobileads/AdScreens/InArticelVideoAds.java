package com.example.mobileads.AdScreens;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mobileads.MainActivity;
import com.example.mobileads.R;
import com.example.mobileads.YeahAdsManager;
import com.google.android.exoplayer2.ui.StyledPlayerView;

public class InArticelVideoAds extends AppCompatActivity {


    StyledPlayerView adView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_articel_video_ads);


        adView = findViewById(R.id.adView);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                YeahAdsManager.showInArticle(adView);
            }
        }, 1500);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(InArticelVideoAds.this, MainActivity.class));
        finish();
    }
}