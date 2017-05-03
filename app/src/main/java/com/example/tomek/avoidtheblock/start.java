package com.example.tomek.avoidtheblock;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

public class start extends AppCompatActivity {

    // https://firebase.google.com/docs/admob/android/interstitial  <-- Guide

    // Creating variable INTERSTITIAL of type InterstitialAd
    private InterstitialAd interstitial;
    final String TEST_DEVICE_1 = "E947D2E35979027CB03816189B5B617E";
    final String FULLSCREEN_AD = "ca-app-pub-1069734417998391/6242556267";
    final String FULLSCREEN_TEST_AD = "ca-app-pub-3940256099942544/1033173712";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        Button startButton = (Button) findViewById(R.id.startButton);

        // Initializing variable INTERSTITIAL of type InterstitialAd
        // "ca-app-pub-3940256099942544/1033173712" is a test ad
        interstitial = new InterstitialAd(this);
        interstitial.setAdUnitId(FULLSCREEN_AD);

        // Creating request
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(TEST_DEVICE_1)
                .build();

        // Start loading
        interstitial.loadAd(adRequest);

        // Display the ad as it is loaded and ready
        interstitial.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                requestNewInterstitial();
                startGame();
            }
        });

        requestNewInterstitial();

        startButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                requestNewInterstitial();
                if(interstitial.isLoaded()){
                    interstitial.show();
                } else {
                    startGame();
                    requestNewInterstitial();
                }

            }
        });

    }

    public void startGame(){
        startActivity(new Intent(getApplicationContext(), main.class));
    }

    // Create ad-requesting method
    private void requestNewInterstitial() {
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(TEST_DEVICE_1)
                .build();

        interstitial.loadAd(adRequest);
    }


    // Disable RETURN button
    @Override
    public boolean dispatchKeyEvent(KeyEvent event){

        if(event.getAction() == KeyEvent.ACTION_DOWN){
            switch(event.getKeyCode()){
                case KeyEvent.KEYCODE_BACK:
                    return true;
            }
        }

        return super.dispatchKeyEvent(event);

    }
}
