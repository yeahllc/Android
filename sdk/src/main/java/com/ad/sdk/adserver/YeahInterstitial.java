package com.ad.sdk.adserver;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.ad.sdk.adserver.Listener.YeahInterstitialAdShowListener;
import com.ad.sdk.adserver.Listener.YeahInterstitialLoadAdListener;
import com.ad.sdk.utils.LoadData;

public class YeahInterstitial {

    public static YeahInterstitialLoadAdListener interstitialVideoAdListener = null;

    public static void setInterstitialVideoAdListener(YeahInterstitialLoadAdListener interstitialVideoAdListener) {
        YeahInterstitial.interstitialVideoAdListener = interstitialVideoAdListener;
    }

    public static void load(Context context, YeahInterstitialLoadAdListener InterstitialLoadListener) {
        try {
            setInterstitialVideoAdListener(InterstitialLoadListener);

//            String ad_check = sharedPreferences.getString("ad_check", "0");


            String getADType = new LoadData().getAdType(context);

            if (getADType.equalsIgnoreCase("INTERSTITIAL_VID")) {
                SharedPreferences sharedPreferences = context.getSharedPreferences("InterstitialVideo", MODE_PRIVATE);
                String ad_url = sharedPreferences.getString("InterstitialVideo_URL", "");
                if (ad_url.length() > 0) {
                    YeahInterstitial.interstitialVideoAdListener.onYeahAdsAdLoaded();
                } else {
                    YeahInterstitial.interstitialVideoAdListener.onYeahAdsAdLoaded();

//                    Log.d("SDK", "No Ads");
                }
            } else {

                SharedPreferences sharedPreferences = context.getSharedPreferences("InterstitialImage", MODE_PRIVATE);
                String ad_url = sharedPreferences.getString("InterstitialImage_URL", "");
                if (ad_url.length() > 0) {
                    YeahInterstitial.interstitialVideoAdListener.onYeahAdsAdLoaded();
                } else {
                    YeahInterstitial.interstitialVideoAdListener.onYeahAdsAdLoaded();

//                    Log.d("SDK", "No Ads");
                }

            }


        } catch (Exception e) {
            Log.d("SDK", "InterstitialVideo Ad Exception:" + e);
        }
    }

    public static void show(Context context, YeahInterstitialAdShowListener videoListener) {

        String getADType = new LoadData().getAdType(context);

        Log.e("Interstitial ADType", ":" + getADType);


        if (getADType.equalsIgnoreCase("INTERSTITIAL_VID")) {
            YeahLoadInterstitial.show(context, videoListener);
        } else {
            YeahInterstitialImage.show(context, videoListener);

        }
    }


}
