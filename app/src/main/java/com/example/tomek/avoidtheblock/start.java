package com.example.tomek.avoidtheblock;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.formats.NativeAd;

import org.w3c.dom.Text;

public class start extends AppCompatActivity {

    TextView titleLabel;
    ImageView points30;
    ImageView points20;
    ImageView points10;
    ImageView life1;
    ImageView gover;
    Switch stonogaSwitch;

    /* //AD
    // https://firebase.google.com/docs/admob/android/interstitial  <-- Guide

    // Creating variable INTERSTITIAL of type InterstitialAd
    private InterstitialAd interstitial;
    final String TEST_DEVICE_1 = "E947D2E35979027CB03816189B5B617E";
    final String FULLSCREEN_AD = "ca-app-pub-1069734417998391/6242556267";
    final String FULLSCREEN_TEST_AD = "ca-app-pub-3940256099942544/1033173712";
    */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        Button startButton = (Button) findViewById(R.id.startButton);
        stonogaSwitch = (Switch) findViewById(R.id.stonogaSwitch);
        titleLabel = (TextView) findViewById(R.id.titleLabel);
        points30 = (ImageView) findViewById(R.id.points30);
        points20 = (ImageView) findViewById(R.id.points20);
        points10 = (ImageView) findViewById(R.id.points10);
        life1 = (ImageView) findViewById(R.id.life1);
        gover = (ImageView) findViewById(R.id.gover);

        /* AD
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
                if(stonogaSwitch.isChecked()){
                    startGameStonoga();
                } else{
                    startGameNormal();
                }
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
                    if(stonogaSwitch.isChecked()){
                        startGameStonoga();
                    } else{
                        startGameNormal();
                    }
                    requestNewInterstitial();
                }

            }
        });

    }*/

        stonogaSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(stonogaSwitch.isChecked()){
                    imagesStonoga();
                }else{
                    imagesNormal();
                }
            }
        });

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (stonogaSwitch.isChecked()) {
                    startGameStonoga();
                } else {
                    startGameNormal();
                }
            }
        });

    }


    public void startGameNormal(){
        startActivity(new Intent(getApplicationContext(), main.class));
    }
    public void startGameStonoga(){
        startActivity(new Intent(getApplicationContext(), mainStonoga.class));
    }

    public void imagesNormal(){
        titleLabel.setText("The Shuriken");

        points30.requestLayout();
        points30.setImageResource(R.drawable.xp_ball);
        points30.getLayoutParams().height = 20;
        points30.getLayoutParams().width = 20;

        points20.requestLayout();
        points20.setImageResource(R.drawable.xp_ball);
        points20.getLayoutParams().height = 12;
        points20.getLayoutParams().width = 12;

        points10.requestLayout();
        points10.setImageResource(R.drawable.xp_ball);
        points10.getLayoutParams().height = 6;
        points10.getLayoutParams().width = 6;

        /*  // Nothing changes here anyway
        life1.requestLayout();
        life1.setImageResource(R.drawable.heart);
        life1.getLayoutParams().height = 15;
        life1.getLayoutParams().width = 15; */

        gover.requestLayout();
        gover.setImageResource(R.drawable.shuriken);
        gover.getLayoutParams().height = 30;
        gover.getLayoutParams().width = 30;
    }
    public void imagesStonoga(){
        titleLabel.setText("The Stonoga");

        points30.requestLayout();
        points30.setImageResource(R.drawable.duda);
        points30.getLayoutParams().height = 35;
        points30.getLayoutParams().width = 35;

        points20.requestLayout();
        points20.setImageResource(R.drawable.ziobro);
        points20.getLayoutParams().height = 25;
        points20.getLayoutParams().width = 25;

        points10.requestLayout();
        points10.setImageResource(R.drawable.swetru);
        points10.getLayoutParams().height = 15;
        points10.getLayoutParams().width = 15;

        /*  // Nothing changes here anyway
        life1.requestLayout();
        life1.setImageResource(R.drawable.heart);
        life1.getLayoutParams().height = 15;
        life1.getLayoutParams().width = 15; */

        gover.requestLayout();
        gover.setImageResource(R.drawable.karakan);
        gover.getLayoutParams().height = 50;
        gover.getLayoutParams().width = 50;
    }

    /* AD
    // Create ad-requesting method
    private void requestNewInterstitial() {
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(TEST_DEVICE_1)
                .build();

        interstitial.loadAd(adRequest);
    } */


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
