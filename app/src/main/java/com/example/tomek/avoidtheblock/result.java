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

import static com.example.tomek.avoidtheblock.R.id.highScoreLabel;

public class result extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        TextView scoreLabel = (TextView) findViewById(R.id.scoreLabel);
        TextView highScoreLabel = (TextView) findViewById(R.id.highScoreLabel);
        Button buttonResetHS = (Button) findViewById(R.id.buttonResetHS);

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


    }

        public void tryAgain(View view) {
            startActivity(new Intent(getApplicationContext(), start.class));
        }

        public void resetHighScore() {
            startActivity(new Intent(getApplicationContext(), start.class));
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
