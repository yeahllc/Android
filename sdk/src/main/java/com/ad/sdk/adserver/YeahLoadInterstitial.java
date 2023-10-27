package com.ad.sdk.adserver;

import static android.content.Context.MODE_PRIVATE;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import com.ad.sdk.adserver.Listener.YeahInterstitialAdShowListener;
import com.ad.sdk.adserver.Listener.YeahInterstitialLoadAdListener;

public class YeahLoadInterstitial {

    public static YeahInterstitialLoadAdListener interstitialVideoAdListener = null;

    public static YeahInterstitialAdShowListener int_show_listener = null;

    public static void setInterstitialVideoAdListener(YeahInterstitialLoadAdListener interstitialVideoAdListener) {
        YeahLoadInterstitial.interstitialVideoAdListener = interstitialVideoAdListener;
    }

    public static void setInt_show_listener(YeahInterstitialAdShowListener int_show_listener) {
        YeahLoadInterstitial.int_show_listener = int_show_listener;
    }

    @SuppressLint("LongLogTag")
    public static void load(Context context, YeahInterstitialLoadAdListener interstitialVideoAdListener) {
        try {
            setInterstitialVideoAdListener(interstitialVideoAdListener);
            SharedPreferences sharedPreferences = context.getSharedPreferences("InterstitialVideo", MODE_PRIVATE);
            String ad_url = sharedPreferences.getString("InterstitialVideo_URL", "");
            String ad_check = sharedPreferences.getString("ad_check", "0");


            if (ad_url.length() > 0) {
                YeahLoadInterstitial.interstitialVideoAdListener.onYeahAdsAdLoaded();
            } else {
                Log.d("SDK", "No Ads");
                YeahLoadInterstitial.interstitialVideoAdListener.onYeahAdsAdFailed();

            }


        } catch (Exception e) {
            Log.d("SDK", "InterstitialVideo Ad Exception:" + e);
        }


    }


    @SuppressLint("LongLogTag")
    public static void show(Context context, YeahInterstitialAdShowListener int_show_listener) {
        setInt_show_listener(int_show_listener);
        Intent i = new Intent(context, InterstitialLoadActivity.class);
//        Log.e("InterstitialVideoStatus:", "" + "Ad is showing");
        context.startActivity(i);
    }

}
