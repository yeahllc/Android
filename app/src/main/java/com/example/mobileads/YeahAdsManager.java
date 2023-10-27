package com.example.mobileads;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import com.ad.sdk.adserver.Listener.AdViewListener;
import com.ad.sdk.adserver.Listener.BannerListener;
import com.ad.sdk.adserver.Listener.YeahInterstitialAdShowListener;
import com.ad.sdk.adserver.Listener.YeahInterstitialLoadAdListener;
import com.ad.sdk.adserver.Listener.YeahRewardedAdLoadListener;
import com.ad.sdk.adserver.Listener.YeahRewardedAdShowListener;
import com.ad.sdk.adserver.YeahAdsInitialize;
import com.ad.sdk.adserver.YeahBannerImageAD;
import com.ad.sdk.adserver.YeahInterstitial;
import com.ad.sdk.adserver.YeahRewardedVideo;


public class YeahAdsManager {

    private static String myZoneId = "27";
    private static Activity mActivity;

    public static void init(Activity appActivity) {
        mActivity = appActivity;

        //Initialize the SDK
        new YeahAdsInitialize(mActivity, myZoneId, new AdViewListener() {
            @Override
            public void onInitializationComplete() {
//                Log.e("SDK_Init_Status : ", "" + "Initialization Successfully");
                loadInterstitialYeahAds();
                loadRewardedYeahAds();
            }

            @Override
            public void onInitializationFailure() {
//                Log.e("SDK_Init_Status : ", "" + "Initialization Failed");
            }
        });

    }


    public static void showBanner() {


        YeahBannerImageAD.show(mActivity, "TOP", new BannerListener() {
            @Override
            public void onYeahAdsAdLoaded() {
                Log.e(" Banner AD Status :", "" + "Loaded");
            }

            @Override
            public void onYeahAdsAdFailed() {
                Log.e(" Banner AD Status :", "" + "Failed");
            }
        });
    }

    public static void loadInterstitialYeahAds() {

        YeahInterstitial.load(mActivity, new YeahInterstitialLoadAdListener() {
            @Override
            public void onYeahAdsAdLoaded() {
//                Log.e("Interstitial Video Load Status :", "" + "Loaded");
            }

            @Override
            public void onYeahAdsAdFailed() {
//                Log.e("Interstitial Video Load Status :", "" + "Failed");
            }
        });

    }


    public static void loadRewardedYeahAds() {
        YeahRewardedVideo.load(mActivity, new YeahRewardedAdLoadListener() {
            @Override
            public void onYeahAdsAdLoaded() {
//                Log.e("Rewarded Video Load Status :", "" + "Loaded");
            }

            @Override
            public void onYeahAdsAdFailed() {
//                Log.e("Rewarded Video Load Status :", "" + "Failed");
            }
        });

    }


    public static void showInterstitialYeahAds() {

        YeahInterstitial.show(mActivity, new YeahInterstitialAdShowListener() {
            @Override
            public void onYeahAdsShowFailure() {
//                Log.e("Interstitial Video Show Status :", "" + "Ad Failed");

            }

            @Override
            public void onYeahAdsShowStart() {
//                Log.e("Interstitial Video Show Status :", "" + "Ad Showed");


            }

            @Override
            public void onYeahAdsShowClick() {
//                Log.e("Interstitial Video Click Status :", "" + "Ad Clicked");

            }

            @Override
            public void onYeahAdsShowComplete() {
//                Log.e("Interstitial Video Show Status :", "" + "Ad Completed");

            }

            @Override
            public void onYeahAdsDismissed() {
//                Log.e("Interstitial Video Show Status :", "" + "Ad Dismissed");

            }


        });
    }


    public static void showRewardedYeahAds() {

        YeahRewardedVideo.show(mActivity, new YeahRewardedAdShowListener() {
            @Override
            public void onYeahAdsShowFailure() {
//                Log.e("Rewarded Video Show Status :", "" + "Ad show Failure");

            }

            @Override
            public void onYeahAdsShowStart() {
//                Log.e("Rewarded Video Click Status :", "" + "Ad Showed");

            }

            @Override
            public void onYeahAdsShowClicked() {
//                Log.e("Rewarded Video Click Status :", "" + "Ad Clicked");

            }

            @Override
            public void onYeahAdsShowComplete() {
//                Log.e("Rewarded Video Show Status :", "" + "Ad Closed");

            }

            @Override
            public void Rewarded(String rewardItem, int rewarded) {
                Toast.makeText(mActivity, "Reward Point : " + rewarded, Toast.LENGTH_SHORT).show();

            }
        });
    }


}