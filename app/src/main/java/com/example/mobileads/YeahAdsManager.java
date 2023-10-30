package com.example.mobileads;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import com.ad.sdk.adserver.Listener.AdViewListener;
import com.ad.sdk.adserver.Listener.BannerListener;
import com.ad.sdk.adserver.Listener.YeahBottomSliderAdListener;
import com.ad.sdk.adserver.Listener.YeahInterstitialAdShowListener;
import com.ad.sdk.adserver.Listener.YeahInterstitialLoadAdListener;
import com.ad.sdk.adserver.Listener.YeahRewardedAdLoadListener;
import com.ad.sdk.adserver.Listener.YeahRewardedAdShowListener;
import com.ad.sdk.adserver.YeahAdsInitialize;
import com.ad.sdk.adserver.YeahBannerImageAD;
import com.ad.sdk.adserver.YeahBannerPosition;
import com.ad.sdk.adserver.YeahBottomSliderAd;
import com.ad.sdk.adserver.YeahDirectLinkAd;
import com.ad.sdk.adserver.YeahHTMLAD;
import com.ad.sdk.adserver.YeahHTML_5_Ad;
import com.ad.sdk.adserver.YeahInArticleVideoAds;
import com.ad.sdk.adserver.YeahInterstitial;
import com.ad.sdk.adserver.YeahRewardedVideo;
import com.ad.sdk.adserver.YeahTopBannerAD;
import com.google.android.exoplayer2.ui.StyledPlayerView;


public class YeahAdsManager {

    private static String myZoneId = "36";
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
        YeahBannerImageAD.show(mActivity, YeahBannerPosition.TOP, new BannerListener() {
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


    public static void showTopBanner() {
        YeahTopBannerAD.show(mActivity, new BannerListener() {
            @Override
            public void onYeahAdsAdLoaded() {
                Log.e(" Top Banner AD Status :", "" + "Loaded");

            }

            @Override
            public void onYeahAdsAdFailed() {
                Log.e(" Top Banner AD Status :", "" + "Failed");

            }
        });

    }

    public static void showBottomSlider() {
        YeahBottomSliderAd.show(mActivity, new YeahBottomSliderAdListener() {
            @Override
            public void onYeahAdsAdLoaded() {

            }

            @Override
            public void onYeahAdsAdFailed() {

            }

            @Override
            public void onYeahAdsAdShown() {

            }

            @Override
            public void onYeahAdsAdClicked() {

            }

            @Override
            public void onYeahAdsAdDismissed() {

            }
        });
    }

    public static void showHTML() {
        YeahHTMLAD.show(mActivity, new BannerListener() {
            @Override
            public void onYeahAdsAdLoaded() {

            }

            @Override
            public void onYeahAdsAdFailed() {

            }
        });

    }


    public static void showHTML_5() {
        YeahHTML_5_Ad.show(mActivity, new BannerListener() {
            @Override
            public void onYeahAdsAdLoaded() {

            }

            @Override
            public void onYeahAdsAdFailed() {

            }
        });
    }

    public static void showInArticle(StyledPlayerView playerView) {
        new YeahInArticleVideoAds().loadAd(playerView, mActivity);
    }

    public static void redirectAds() {
        YeahDirectLinkAd.show(mActivity, "Link Title", new BannerListener() {
            @Override
            public void onYeahAdsAdLoaded() {

            }

            @Override
            public void onYeahAdsAdFailed() {

            }
        });
    }

}