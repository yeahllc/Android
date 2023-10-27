package com.example.mobileads;

import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

// Initialise
        YeahAdsManager.init(MainActivity.this);


        ((Button) findViewById(R.id.bannerAd)).setOnClickListener(view -> {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    YeahAdsManager.showBanner();
                }
            }, 1200);
        });

        ((Button) findViewById(R.id.interstitialAd)).setOnClickListener(view -> {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    YeahAdsManager.showInterstitialYeahAds();
                }
            }, 1200);
        });


        //Load and show the Rewarded Video
        ((Button) findViewById(R.id.rewardedVideoAd)).setOnClickListener(view -> {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    YeahAdsManager.showRewardedYeahAds();
                }
            }, 1500);
        });

    }
}
