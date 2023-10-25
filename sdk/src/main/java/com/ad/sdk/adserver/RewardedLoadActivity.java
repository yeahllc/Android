package com.ad.sdk.adserver;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.multidex.MultiDex;

import com.ad.sdk.R;
import com.bumptech.glide.Glide;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.PlaybackException;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.ext.ima.ImaAdsLoader;
import com.google.android.exoplayer2.source.DefaultMediaSourceFactory;
import com.google.android.exoplayer2.source.MediaSourceFactory;
import com.google.android.exoplayer2.ui.StyledPlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSource;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.Util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public class RewardedLoadActivity extends AppCompatActivity {
    private StyledPlayerView playerView;
    private ExoPlayer player;
    private ImaAdsLoader adsLoader;

    int amount;

    int count = 0;

    private ImageView logo;

    private LinearLayout rewardGranted, suggestionPart;

    int totalSeconds;

    int rewardGrantedTime;

    int getVideoRewardStopTime;

    private String destination_url = null, decodeDest_url;

    private String logoURL = null;

    private boolean on_pause = false;


    @SuppressLint("QueryPermissionsNeeded")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.reward_adactivity);
        MultiDex.install(this);
        suggestionPart = findViewById(R.id.suggestionPart);
        logo = findViewById(R.id.logo);
        rewardGranted = findViewById(R.id.rewardGranted);
        playerView = findViewById(R.id.player_view);
        playerView.setControllerAutoShow(false);
        count = 0;
        if (YeahRewardedVideo.rewardedListener != null) {
            YeahRewardedVideo.rewardedListener.onYeahAdsAdLoaded();
        }

        // Create an AdsLoader.
        adsLoader = new ImaAdsLoader.Builder(this).build();


        ConnectivityManager conMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = conMgr.getActiveNetworkInfo();

        ((ImageView) findViewById(R.id.img_close_btn)).setVisibility(View.GONE);

        ((ImageView) findViewById(R.id.img_close_btn)).setOnClickListener(view -> {
            playerView.onPause();
            adsLoader.setPlayer(null);
            playerView.setPlayer(null);

            if (!(netInfo == null)) {

                if (YeahRewardedVideo.rewardedshowListener != null) {
                    YeahRewardedVideo.rewardedshowListener.Rewarded("Reward Points", amount);
                    Log.e("Ad", ":" + "Completed");
                }
            }

            finish();
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        });


        SharedPreferences sharedPreferences = getSharedPreferences("reward_amount", MODE_PRIVATE);
        String string_amount = sharedPreferences.getString("amount", "0");

        SharedPreferences sharedPreferences1 = getSharedPreferences("RewardedVideo", MODE_PRIVATE);

        destination_url = sharedPreferences1.getString("destinationURL", "www.google.com");
        logoURL = sharedPreferences1.getString("logoURL", "https://cpng.pikpng.com/pngl/s/209-2090783_own-logo-insert-love-food-hate-waste-logo.png");
        try {
            decodeDest_url = URLDecoder.decode(destination_url, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }

        ((Button) findViewById(R.id.desc_btn)).setOnClickListener(view -> {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(decodeDest_url));

            if (browserIntent.resolveActivity(getPackageManager()) != null) {
                startActivity(browserIntent);
            }
        });

        Glide.with(this).load(logoURL).into(logo);


        if (string_amount.equalsIgnoreCase("")) {
            amount = 0;
        } else {
            amount = Integer.parseInt(string_amount);
        }


        Log.e("Add Amount", String.valueOf(amount));


    }

    private void releasePlayer() {
        adsLoader.setPlayer(null);
        playerView.setPlayer(null);
        player.release();
        player = null;
    }

    private void initializePlayer() {
        // Set up the factory for media sources, passing the ads loader and ad view providers.
        DataSource.Factory dataSourceFactory = new DefaultDataSource.Factory(this);

        MediaSourceFactory mediaSourceFactory = new DefaultMediaSourceFactory(dataSourceFactory).setAdsLoaderProvider(unusedAdTagUri -> adsLoader).setAdViewProvider(playerView);

        // Create an ExoPlayer and set it as the player for content and ads.
        player = new ExoPlayer.Builder(this).setMediaSourceFactory(mediaSourceFactory).build();
        playerView.setPlayer(player);
        adsLoader.setPlayer(player);

        SharedPreferences sharedPreferences = getSharedPreferences("RewardedVideo", MODE_PRIVATE);
        String ad_url = sharedPreferences.getString("RewardedVideo_URL", "");
        String screenTpye = sharedPreferences.getString("screenType", "");
        if (screenTpye.equals("portrait")) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
            if (getSupportActionBar() != null) {
                getSupportActionBar().show();
            }
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
        } else {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
            if (getSupportActionBar() != null) {
                getSupportActionBar().show();
            }
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }

        System.out.println("@@ RewardedVideo_URL ad_url " + ad_url);

        Uri contentUri = Uri.parse("test");
        Uri adTagUri = Uri.parse(ad_url);
        MediaItem mediaItem = new MediaItem.Builder().setUri(contentUri).setAdsConfiguration(new MediaItem.AdsConfiguration.Builder(adTagUri).build()).build();

        // Prepare the content and ad to be played with the SimpleExoPlayer.
        player.setMediaItem(mediaItem);
        player.prepare();


        // Set PlayWhenReady. If true, content and ads will autoplay.
        player.setPlayWhenReady(true);


        player.addListener(new Player.Listener() {
            @Override
            public void onPlaybackStateChanged(int playbackState) {

                if (playbackState == player.STATE_READY) {
                    suggestionPart.setVisibility(View.VISIBLE);
                    YeahRewardedVideo.rewardedshowListener.onYeahAdsShowStart();
                    Log.e("Ad", ":" + "Started");

                    long realDurationMillis = player.getDuration();
                    totalSeconds = (int) realDurationMillis;
                    rewardGrantedTime = totalSeconds * 65 / 100;
                }


                if (player.getPlaybackState() == player.STATE_BUFFERING) {
                    Log.e("Ad", ":" + "Buffering");
                }


                if (player.getPlaybackState() == player.STATE_IDLE) {

                    ((ImageView) findViewById(R.id.img_close_btn)).setVisibility(View.VISIBLE);

//                    ConnectivityManager conMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
//                    NetworkInfo netInfo = conMgr.getActiveNetworkInfo();
//
//                    if (!(netInfo == null)) {
//
//                        if (YeahRewardedVideo.rewardedshowListener != null) {
//                            YeahRewardedVideo.rewardedshowListener.Rewarded("Reward Points", amount);
//                            Log.e("Ad", ":" + "Completed");
//                        }
//                    }
                }
            }

            @Override
            public void onPlayerError(PlaybackException error) {
                Log.e("Player_Error", ":" + error.getMessage());
            }


            @Override
            public void onIsPlayingChanged(boolean isPlaying) {


                if (isPlaying) {
                    playerView.postDelayed(this::getCurrentPlayerPosition, 1000);
                }
            }

            private void getCurrentPlayerPosition() {
                if (!on_pause) {
//                    android.util.Log.e("OnPause Status", String.valueOf(on_pause));
//
//                    android.util.Log.e("Player_seconds", String.valueOf(getVideoRewardStopTime));
                    if (player.isPlaying()) {
                        getVideoRewardStopTime = (int) player.getCurrentPosition();
                        playerView.postDelayed(this::getCurrentPlayerPosition, 1000);
                        if (rewardGrantedTime / 1000 == getVideoRewardStopTime / 1000) {
                            rewardGranted.setVisibility(View.VISIBLE);
                        }
                    }

                }
            }
        });


    }

    @Override
    public void onStart() {
        super.onStart();
        //
        if (Util.SDK_INT > 23) {
            initializePlayer();
            if (playerView != null) {
                android.util.Log.d("PlayerView", "Start");
                playerView.onResume();
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        if (Util.SDK_INT <= 23) {
//            initializePlayer();
            if (playerView != null) {
                android.util.Log.d("PlayerView", "Resume");

                playerView.onResume();
            }
        }
    }

    @Override
    public void onPause() {
        on_pause = true;
        super.onPause();
        if (Util.SDK_INT <= 23) {
            if (playerView != null) {
                android.util.Log.d("PlayerView", "Pause");

                playerView.onPause();
            }
//            releasePlayer();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        on_pause = false;

        if (Util.SDK_INT > 23) {
            if (playerView != null) {
                android.util.Log.d("PlayerView", "Stop");

                playerView.onPause();
            }
            releasePlayer();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        adsLoader.release();
        android.util.Log.d("PlayerView", "RewardActivity CLosed");

    }

}