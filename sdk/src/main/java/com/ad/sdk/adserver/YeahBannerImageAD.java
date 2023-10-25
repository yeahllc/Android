package com.ad.sdk.adserver;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.ad.sdk.adserver.Listener.BannerListener;
import com.ad.sdk.utils.LoadData;

public class YeahBannerImageAD {

    static Context context;

    @SuppressLint("SetJavaScriptEnabled")
    public static void show(Context adViewContext, String position, BannerListener listener) {

        context = adViewContext;
        Activity mActivity = (Activity) adViewContext;

        //Add this wherever your code needs to add the ad
        LinearLayout layout = new LinearLayout(mActivity);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layout.setLayoutParams(params);

        //Additionally to adjust the position to Bottom
        layout.setGravity(Gravity.BOTTOM);

        // Create a banner ad
        WebView webView = new WebView(mActivity);

        String PortraitHtmlCode = new LoadData().loadBannerImage(mActivity);
        String LandScapeHtmlCode = new LoadData().loadLandscapeBannerImage(mActivity);

        if (PortraitHtmlCode.isEmpty()) {
            listener.onYeahAdsAdFailed();
        }

        int maxLogSize = 4000;
        for (int j = 0; j <= PortraitHtmlCode.length() / maxLogSize; j++) {
            int start = j * maxLogSize;
            int end = (j + 1) * maxLogSize;
            end = Math.min(end, PortraitHtmlCode.length());
        }
//
//        webView.setBackgroundColor(0);
//        webView.setPadding(0, 0, 0, 0);
//        webView.getSettings().setJavaScriptEnabled(true);
//        String html = "<!DOCTYPE html><html>" + "<style type='text/css'>" + "html,body {margin: 0;padding: 0;width: 100%;height: 100%;}" + "html {display: table;}" + "body {display: table-cell;vertical-align: middle;text-align: center;}" + "img{display: inline;height: auto;max-width: 100%;}" + "</style>" + "<body style= \"width=\"100%\";height=\"100%\";initial-scale=\"1.0\"; maximum-scale=\"1.0\"; user-scalable=\"no\";>" + PortraitHtmlCode + "</body></html>";
//
//        webView.loadData(html, "text/html", "UTF-8");
//        webView.setClickable(true);
//        webView.setVerticalScrollBarEnabled(false);
//        webView.setHorizontalScrollBarEnabled(false);


        int orientation = mActivity.getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            Log.e("Orientation", "" + "Portrait");


            webView.setBackgroundColor(0);
            webView.setPadding(0, 0, 0, 0);
            webView.getSettings().setJavaScriptEnabled(true);
            String html = "<!DOCTYPE html><html>" + "<style type='text/css'>" + "html,body {margin: 0;padding: 0;width: 100%;height: 100%;}" + "html {display: table;}" + "body {display: table-cell;vertical-align: middle;text-align: center;}" + "img{display: inline;height: auto;max-width: 100%;}" + "</style>" + "<body style= \"width=\"100%\";height=\"100%\";initial-scale=\"1.0\"; maximum-scale=\"1.0\"; user-scalable=\"no\";>" + PortraitHtmlCode + "</body></html>";

            webView.loadData(html, "text/html", "UTF-8");
            webView.setClickable(true);
            webView.setVerticalScrollBarEnabled(false);
            webView.setHorizontalScrollBarEnabled(false);

        } else {
            Log.e("Orientation", "" + "Landscape");

            webView.setBackgroundColor(Color.GRAY);
            webView.setPadding(0, 0, 0, 0);
            webView.getSettings().setJavaScriptEnabled(true);
            String html = "<!DOCTYPE html><html>" + "<style type='text/css'>" + "html,body {margin: 0;padding: 0;width: 100%;height: 100%;}" + "html {display: table;}" + "body {display: table-cell;vertical-align: middle;text-align: center;}" + "img{display: inline;height: auto;max-width: 100%;}" + "</style>" + "<body style= \"width=\"100%\";height=\"100%\";initial-scale=\"1.0\"; maximum-scale=\"1.0\"; user-scalable=\"no\";>" + LandScapeHtmlCode + "</body></html>";

            webView.loadData(html, "text/html", "UTF-8");
            webView.setClickable(true);
            webView.setVerticalScrollBarEnabled(false);
            webView.setHorizontalScrollBarEnabled(false);


//            Display display = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
//            int width = display.getWidth();
//            int height = display.getHeight();
//
//            int pixeldpi = Resources.getSystem().getDisplayMetrics().densityDpi;
//
//
//            int width_dp = (width / pixeldpi) * 160;
//            int height_dp = (height / pixeldpi) * 160;
//
//            Log.d("Landscape", "width: " + width);
//            Log.d("Landscape", "widthInDP: " + width_dp);
//            Log.d("Landscape", "height: " + height);
//            Log.d("Landscape", "heightInDP: " + height_dp);
//            Log.d("Landscape", "density: " + pixeldpi);
//
//
//            LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//            webView.setLayoutParams(params2);

        }

        layout.addView(webView);


        if (position.equalsIgnoreCase("TOP")) {
            ViewGroup.LayoutParams bannerLayout = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.TOP | Gravity.CENTER_HORIZONTAL);
            mActivity.addContentView(layout, bannerLayout);
        } else {
            ViewGroup.LayoutParams bannerLayout = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL);
            mActivity.addContentView(layout, bannerLayout);
        }


        if (!PortraitHtmlCode.isEmpty()) {
            listener.onYeahAdsAdLoaded();
        }


    }


}


