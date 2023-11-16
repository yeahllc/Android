package com.example.mobileads;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mobileads.AdScreens.BannerAd;
import com.example.mobileads.AdScreens.BottomSlider;
import com.example.mobileads.AdScreens.HTMLAd;
import com.example.mobileads.AdScreens.HTML_5_Ad;
import com.example.mobileads.AdScreens.InArticelVideoAds;
import com.example.mobileads.AdScreens.RedirectLinkAd;
import com.example.mobileads.AdScreens.RewardedAds;
import com.example.mobileads.AdScreens.TopBanner;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // Initialise
        YeahAdsManager.init(MainActivity.this);


        //Banner Ad
        ((Button) findViewById(R.id.bannerAd)).setOnClickListener(view -> {
            startActivity(new Intent(MainActivity.this, BannerAd.class));
            finish();
        });

        //Top Banner Ad
        ((Button) findViewById(R.id.topBanner)).setOnClickListener(view -> {
            startActivity(new Intent(MainActivity.this, TopBanner.class));
            finish();
        });


        //Redirect ad
        ((Button) findViewById(R.id.redirectAd)).setOnClickListener(view -> {
            startActivity(new Intent(MainActivity.this, RedirectLinkAd.class));
            finish();
        });


        //HTML Ad
        ((Button) findViewById(R.id.htmlAd)).setOnClickListener(view -> {
            startActivity(new Intent(MainActivity.this, HTMLAd.class));
            finish();
        });


        //HTML 5 Ad
        ((Button) findViewById(R.id.html5Ad)).setOnClickListener(view -> {
            startActivity(new Intent(MainActivity.this, HTML_5_Ad.class));
            finish();
        });


        //Interstitial Ad
        ((Button) findViewById(R.id.interstitialAd)).setOnClickListener(view -> {
//            startActivity(new Intent(MainActivity.this, InterstitialAds.class));
//            finish();

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    YeahAdsManager.showInterstitialYeahAds(MainActivity.this);
                }
            }, 1200);

        });


        //Rewarded Ad
        ((Button) findViewById(R.id.rewardedVideoAd)).setOnClickListener(view -> {
            startActivity(new Intent(MainActivity.this, RewardedAds.class));
            finish();
        });


        //Bottom Slider Ad
        ((Button) findViewById(R.id.bottomSlider)).setOnClickListener(view -> {

            startActivity(new Intent(MainActivity.this, BottomSlider.class));
            finish();
        });

        //InArticle Ad
        ((Button) findViewById(R.id.inArticle)).setOnClickListener(view -> {


            startActivity(new Intent(MainActivity.this, InArticelVideoAds.class));
            finish();

        });
    }
}
