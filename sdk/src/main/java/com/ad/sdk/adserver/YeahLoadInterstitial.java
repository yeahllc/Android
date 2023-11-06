package com.ad.sdk.adserver;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;

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
    public static void show(Context context, YeahInterstitialAdShowListener int_show_listener) {
        setInt_show_listener(int_show_listener);
        Intent i = new Intent(context, InterstitialLoadActivity.class);
//        Log.e("InterstitialVideoStatus:", "" + "Ad is showing");
        context.startActivity(i);
    }

}
