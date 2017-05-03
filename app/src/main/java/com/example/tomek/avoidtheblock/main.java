package com.example.tomek.avoidtheblock;

import android.content.Intent;
import android.graphics.Point;
import android.media.Image;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.RunnableFuture;

public class main extends AppCompatActivity {

    private TextView scoreLabel;
    private TextView startLabel;
    private ImageView playerBox;
    private ImageView xpBall;
    private ImageView xpBall2;
    private ImageView xpBall3;
    private ImageView shuriken;

    // Size
    private int frameHeight;
    private int playerBoxSize;
    private int screenWidth;
    private int screenHeight;

    // Position
    private int playerBoxY;
    private int xpBallX;
    private int xpBallY;
    private int xpBall2X;
    private int xpBall2Y;
    private int xpBall3X;
    private int xpBall3Y;
    private int shurikenX;
    private int shurikenY;

    // Score
    private int score = 0;

    // Speed
    int playerBoxSpeed;
    int xpBallSpeed;
    int xpBall2Speed;
    int xpBall3Speed;
    int shurikenSpeed;

    // Initialize Class
    private Handler handler = new Handler();
    private Timer timer = new Timer();
    private SoundPlayer sound;

    // Status Check
    public boolean action_flg = false;
    private boolean start_flg = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sound = new SoundPlayer(this);

        // Przypisanie konkretnych elementów do konkretnych zmiennych
        scoreLabel = (TextView) findViewById(R.id.scoreLabel);
        startLabel = (TextView) findViewById(R.id.startLabel);
        playerBox = (ImageView) findViewById(R.id.playerBox);
        xpBall = (ImageView) findViewById(R.id.xpBall);
        xpBall2 = (ImageView) findViewById(R.id.xpBall2);
        xpBall3 = (ImageView) findViewById(R.id.xpBall3);
        shuriken = (ImageView) findViewById(R.id.shuriken);

        // Move to out of screen
        xpBall.setX(900);
        xpBall.setY(900);
        xpBall2.setX(900);
        xpBall2.setY(900);
        xpBall3.setX(900);
        xpBall3.setY(900);
        shuriken.setX(900);
        shuriken.setY(900);

        scoreLabel.setText("Score: 0");

    }

    public void changePos(){

        hitCheck();

        // xpBall
        xpBallX -= xpBallSpeed;
        if(xpBallX < 0 || xpBallX == 900){
            xpBallX = screenWidth + 20;
            xpBallY = (int) Math.floor(Math.random() * (frameHeight - xpBall.getHeight()));
        }
        xpBall.setX(xpBallX);
        xpBall.setY(xpBallY);

        // xpBall2
        xpBall2X -= xpBall2Speed;
        if(xpBall2X < 0 || xpBallX == 900){
            xpBall2X = screenWidth + 50;
            xpBall2Y = (int) Math.floor(Math.random() * (frameHeight - xpBall2.getHeight()));
        }
        xpBall2.setX(xpBall2X);
        xpBall2.setY(xpBall2Y);

        // xpBall3
        xpBall3X -= xpBall3Speed;
        if(xpBall3X < 0 || xpBallX == 900){
            xpBall3X = screenWidth + 1000;
            xpBall3Y = (int) Math.floor(Math.random() * (frameHeight - xpBall3.getHeight()));
        }
        xpBall3.setX(xpBall3X);
        xpBall3.setY(xpBall3Y);

        // shuriken
        shurikenX -= shurikenSpeed;
        if(shurikenX < 0){
            shurikenX = screenWidth + 10;
            shurikenY = (int) Math.floor(Math.random() * (frameHeight - shuriken.getHeight()));
        }
        shuriken.setX(shurikenX);
        shuriken.setY(shurikenY);


        // Move Box
        if (action_flg == true) {
            //When touching the screen
            playerBoxY -= playerBoxSpeed;
        } else if (action_flg == false) {
            //When NOT touching the screen
            playerBoxY += playerBoxSpeed;
        }

        // Check box position
        if(playerBoxY < 0) playerBoxY = 0;
        if(playerBoxY > (frameHeight - playerBoxSize)) playerBoxY = frameHeight - playerBoxSize;

        playerBox.setY(playerBoxY);

        // Get screen size
        WindowManager wm = getWindowManager();
        Display disp = wm.getDefaultDisplay();
        Point size = new Point();
        disp.getSize(size);

        screenWidth = size.x;
        screenHeight = size.y;

        // Optimization for multiple devices, so that the speedes are nearly the same
        // Galaxy S2 width:480 height:800
        // Speed:  box:20, xp1:7 xp2:8 xp3:13 shuriken:10

        playerBoxSpeed = Math.round(screenHeight / 40F); // 800 / 40 = 20
        xpBallSpeed = Math.round(screenWidth / 68.5F); // 480 / 68 = 7,007
        xpBall2Speed = Math.round(screenWidth / 60F); // 480 / 60 = 80
        xpBall3Speed = Math.round(screenWidth / 36.92F); // 480 / 36.92 =  13
        shurikenSpeed = Math.round(screenWidth / 48); // 480 / 48 = 10

        // This will display speeds in console log -- check if calculated properly
        Log.v("SPEED_BOX", playerBoxSpeed+"");
        Log.v("SPEED_XPBALL", xpBallSpeed+"");
        Log.v("SPEED_XPBALL2", xpBall2Speed+"");
        Log.v("SPEED_XPBALL3", xpBall3Speed+"");
        Log.v("SPEED_SHURIKEN", shurikenSpeed+"");


    }

    public void hitCheck(){

        // If the center of a ball is in the box, it counts as hit

        // xpBall
        int xpBallCenterX = xpBallX + xpBall.getWidth() / 2;
        int xpBallCenterY = xpBallY + xpBall.getHeight() / 2;

        // 0 <= xpBallCenterX <= playerBoxWidth
        // playerBoyY <= xpBallCenterY <= playerBoxY + boxHeight

        if(0 <= xpBallCenterX && xpBallCenterX <= playerBoxSize &&
                playerBoxY <= xpBallCenterY && xpBallCenterY <= (playerBoxY + playerBoxSize)){
            sound.playHitSound();
            xpBallX = -10;
            score += 10;
            scoreLabel.setText("Score: "+score);


        }

        // xpBall2
        int xpBall2CenterX = xpBall2X + xpBall2.getWidth() / 2;
        int xpBall2CenterY = xpBall2Y + xpBall2.getHeight() / 2;

        if(0 <= xpBall2CenterX && xpBall2CenterX <= playerBoxSize &&
                playerBoxY <= xpBall2CenterY && xpBall2CenterY <= (playerBoxY + playerBoxSize)){
            sound.playHitSound();
            xpBall2X = -10;
            score += 20;
            scoreLabel.setText("Score: "+score);


        }

        // xpBall3
        int xpBall3CenterX = xpBall3X + xpBall3.getWidth() / 2;
        int xpBall3CenterY = xpBall3Y + xpBall3.getHeight() / 2;

        if(0 <= xpBall3CenterX && xpBall3CenterX <= playerBoxSize &&
                playerBoxY <= xpBall3CenterY && xpBall3CenterY <= (playerBoxY + playerBoxSize)){
            sound.playHitSound();
            xpBall3X = -10;
            score += 30;
            scoreLabel.setText("Score: "+score);


        }

        // Shuriken
        int shurikenCenterX = shurikenX + shuriken.getWidth() / 2;
        int shurikenCenterY = shurikenY + shuriken.getHeight() / 2;

        if(0 <= shurikenCenterX && shurikenCenterX <= playerBoxSize &&
                playerBoxY <= shurikenCenterY && shurikenCenterY <= (playerBoxY + playerBoxSize)){

            // Stop timer!
            timer.cancel();
            timer = null;
            sound.playOverSound();

            // Show result -- showing another activity
            Intent intent = new Intent(getApplicationContext(), result.class);
            intent.putExtra("SCORE", score);
            startActivity(intent);

        }
    }

    public boolean onTouchEvent(MotionEvent me){
        if(start_flg == false){
            start_flg = true;

            // Why get frame height and box height here?
            // Because the UI has not been set on the screen in OnCreate()!!

            FrameLayout frame = (FrameLayout)findViewById(R.id.frame);
            frameHeight = frame.getHeight();

            playerBoxY = (int)playerBox.getY();
            // The box is a square (height = width)
            playerBoxSize = playerBox.getHeight();


            startLabel.setVisibility(View.GONE);

            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            changePos();
                        }
                    });
                }
            }, 0, 20);

        } else {
            if (me.getAction() == MotionEvent.ACTION_DOWN) {
                action_flg = true;
            } else if (me.getAction() == MotionEvent.ACTION_UP) {
                action_flg = false;
            }

        }
        return true;
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
