package com.ad.sdk.adserver;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
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
import com.google.android.exoplayer2.util.Util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public class InterstitialLoadActivity extends AppCompatActivity {
    private StyledPlayerView playerView;
    private ExoPlayer player;
    private ImaAdsLoader adsLoader;

    private ImageView logo;

    private String destination_url = null, decodeDest_url;
    private String logoURL = null;

    private LinearLayout suggestionPart;


    @SuppressLint("QueryPermissionsNeeded")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.int_adactivity_main);
        MultiDex.install(this);
        logo = findViewById(R.id.logo);
        suggestionPart = findViewById(R.id.suggestionPart);
        playerView = findViewById(R.id.player_view);
        playerView.setControllerAutoShow(false);
        // Create an AdsLoader.
        adsLoader = new ImaAdsLoader.Builder(this).build();

        ((ImageView) findViewById(R.id.img_close_btn)).setOnClickListener(view -> {
            YeahLoadInterstitial.int_show_listener.onYeahAdsDismissed();
            playerView.onPause();
            adsLoader.setPlayer(null);
            playerView.setPlayer(null);
            finish();
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        });

        SharedPreferences sharedPreferences = getSharedPreferences("InterstitialVideo", MODE_PRIVATE);

        destination_url = sharedPreferences.getString("destination_url", "www.google.com");
        logoURL = sharedPreferences.getString("logoURL", "https://cpng.pikpng.com/pngl/s/209-2090783_own-logo-insert-love-food-hate-waste-logo.png");

        try {
            decodeDest_url = URLDecoder.decode(destination_url, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }


        Log.e("DestinationURL", "" + decodeDest_url);

        ((Button) findViewById(R.id.desc_btn)).setOnClickListener(view -> {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(decodeDest_url));

            if (browserIntent.resolveActivity(getPackageManager()) != null) {
                startActivity(browserIntent);
            }
        });

        Glide.with(this).load(logoURL).into(logo);


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

        SharedPreferences sharedPreferences = getSharedPreferences("InterstitialVideo", MODE_PRIVATE);
        String ad_url = sharedPreferences.getString("InterstitialVideo_URL", "");
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

        System.out.println("@@ ad_url " + ad_url);
        Uri contentUri = Uri.parse("test");

        Uri adTagUri = Uri.parse(ad_url);

        System.out.println("@@ adTagUri " + adTagUri);

        MediaItem mediaItem = new MediaItem.Builder().setUri(contentUri).setAdsConfiguration(new MediaItem.AdsConfiguration.Builder(adTagUri).build()).build();

        // Prepare the content and ad to be played with the SimpleExoPlayer.
        player.setMediaItem(mediaItem);
        player.prepare();

        // Set PlayWhenReady. If true, content and ads will autoplay.
        player.setPlayWhenReady(true);
        player.addListener(new Player.Listener() {

            @Override
            public void onPlaybackStateChanged(int playbackState) {

                if (playbackState == Player.STATE_ENDED) {
                    YeahLoadInterstitial.int_show_listener.onYeahAdsShowComplete();
                } else if (playbackState == Player.EVENT_PLAYER_ERROR) {
                    YeahLoadInterstitial.int_show_listener.onYeahAdsShowFailure();
                } else if (playbackState == Player.STATE_READY) {
                    suggestionPart.setVisibility(View.VISIBLE);
                }
            }


            @Override
            public void onPlayerError(PlaybackException error) {
                com.google.android.exoplayer2.util.Log.e("Player_Error", ":" + error.getMessage());
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
                playerView.onResume();
                YeahLoadInterstitial.int_show_listener.onYeahAdsShowStart();
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (Util.SDK_INT <= 23) {
//            initializePlayer();
            if (playerView != null) {
                playerView.onResume();
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (Util.SDK_INT <= 23) {
            if (playerView != null) {
                playerView.onPause();
            }
//            releasePlayer();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT > 23) {
            if (playerView != null) {
                playerView.onPause();
            }
            releasePlayer();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        adsLoader.release();
    }

}