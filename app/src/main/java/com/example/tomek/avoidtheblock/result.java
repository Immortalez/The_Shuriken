package com.example.tomek.avoidtheblock;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

import static com.example.tomek.avoidtheblock.R.id.highScoreLabel;
import static com.example.tomek.avoidtheblock.R.id.startButton;
import static com.example.tomek.avoidtheblock.R.id.stonogaSwitch;
import static com.example.tomek.avoidtheblock.R.id.tryAgainButton;

public class result extends AppCompatActivity {

    TextView scoreLabel;
    TextView highScoreLabel;
    Button buttonResetHS;
    Button tryAgainButton;
    // https://firebase.google.com/docs/admob/android/interstitial  <-- Guide

    // Creating variable INTERSTITIAL of type InterstitialAd
    private InterstitialAd interstitial;
    final String TEST_DEVICE_1 = "E947D2E35979027CB03816189B5B617E";
    final String FULLSCREEN_AD = "ca-app-pub-1069734417998391/6242556267";
    final String FULLSCREEN_TEST_AD = "ca-app-pub-3940256099942544/1033173712";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        scoreLabel = (TextView) findViewById(R.id.scoreLabel);
        highScoreLabel = (TextView) findViewById(R.id.highScoreLabel);
        buttonResetHS = (Button) findViewById(R.id.buttonResetHS);
        tryAgainButton = (Button) findViewById(R.id.tryAgainButton);




        int score = getIntent().getIntExtra("SCORE", 0);
        scoreLabel.setText(score + ""); // + "" żeby nie robić String.valueOf(score)

        SharedPreferences settings = getSharedPreferences("GAME_DATA", Context.MODE_PRIVATE);
        int highScore = settings.getInt("HIGH_SCORE", 0);

        if (score > highScore) {
            highScoreLabel.setText("High Score: " + score);

            // Save
            SharedPreferences.Editor editor = settings.edit();
            editor.putInt("HIGH_SCORE", score);
            editor.commit();

        } else {
            highScoreLabel.setText("High Score: " + highScore);
        }

        buttonResetHS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetHighScore();
            }
        });



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
                startActivity(new Intent(getApplicationContext(), start.class));
            }
        });

        requestNewInterstitial();


        tryAgainButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                requestNewInterstitial();
                if(interstitial.isLoaded()){
                    interstitial.show();
                } else {
                    startActivity(new Intent(getApplicationContext(), start.class));
                    requestNewInterstitial();
                }

            }
        });


    }

        // Create ad-requesting method
        private void requestNewInterstitial() {
            AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(TEST_DEVICE_1)
                .build();

            interstitial.loadAd(adRequest);
        }


        public void resetHighScore() {
            highScoreLabel.setText("High Score: 0");
            SharedPreferences settings = getSharedPreferences("GAME_DATA", Context.MODE_PRIVATE);
            int highScore = settings.getInt("HIGH_SCORE", 0);

            // Save
            SharedPreferences.Editor editor = settings.edit();
            editor.putInt("HIGH_SCORE", 0);
            editor.commit();
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
