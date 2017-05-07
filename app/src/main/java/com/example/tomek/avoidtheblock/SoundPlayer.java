package com.example.tomek.avoidtheblock;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;

/**
 * Created by Tomek on 03.05.2017.
 */

public class SoundPlayer {

    private AudioAttributes audioAttributes;
    final int SOUND_POOL_MAX = 3;


    private static SoundPool soundPool;
    private static int hitSound;
    private static int addHeartSound;
    private static int overSound;

    private static int witamSound;
    private static int dudaSound;
    private static int ziobroSound;
    private static int swetruSound;
    private static int addHeartStonogaSound;
    private static int karakanSound;
    private static int zegnamSound;



    public SoundPlayer(Context context){


        // SoundPool is deprecated in API level 21 (Lollipop)
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){

            audioAttributes = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_GAME)
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .build();

            soundPool = new SoundPool.Builder()
                    .setAudioAttributes(audioAttributes)
                    .setMaxStreams(SOUND_POOL_MAX)
                    .build();

        } else{
            //SoundPool (int maxStreams, int streamType, int intsrcQuality)
            soundPool = new SoundPool(SOUND_POOL_MAX, AudioManager.STREAM_MUSIC, 0);

        }

        hitSound = soundPool.load(context, R.raw.hit, 1);
        overSound = soundPool.load(context, R.raw.over, 1);
        // addHeartSound = soundPool.load(context, R.raw., 1);  // <-- find proper sound

        // Stonoga Mode
        witamSound = soundPool.load(context, R.raw.wita, 1);
        zegnamSound = soundPool.load(context, R.raw.zegnam, 1);
        dudaSound = soundPool.load(context, R.raw.duda, 3);
        ziobroSound = soundPool.load(context, R.raw.ziobro, 3);
        swetruSound = soundPool.load(context, R.raw.swetru, 3);
        addHeartStonogaSound = soundPool.load(context, R.raw.heart, 1);
        karakanSound = soundPool.load(context, R.raw.karakan, 3);




    }


    public void playHitSound(){
        // play(int soundID, float leftVolume, float rightVolume, int priority, int loop, float rate)
        soundPool.play(hitSound, 1.0f, 1.0f, 1, 0, 1.0f);
    }
    public void playOverSound(){
        // play(int soundID, float leftVolume, float rightVolume, int priority, int loop, float rate)
        soundPool.play(overSound, 1.0f, 1.0f, 1, 0, 1.0f);
    }
    public void playAddHeartSound(){
        // play(int soundID, float leftVolume, float rightVolume, int priority, int loop, float rate)
        soundPool.play(addHeartSound, 1.0f, 1.0f, 1, 0, 1.0f);
    }


    public void playStonogaWitamSound(){
        // play(int soundID, float leftVolume, float rightVolume, int priority, int loop, float rate)
        soundPool.play(witamSound, 1.0f, 1.0f, 1, 0, 1.0f);
    }
    public void playStonogaZegnamSound(){
        // play(int soundID, float leftVolume, float rightVolume, int priority, int loop, float rate)
        soundPool.play(zegnamSound, 1.0f, 1.0f, 1, 0, 1.0f);
    }
    public void playStonogaaddHeartSound(){
        // play(int soundID, float leftVolume, float rightVolume, int priority, int loop, float rate)
        soundPool.play(addHeartStonogaSound, 1.0f, 1.0f, 1, 0, 1.0f);
    }
    public void playStonogaDudaSound(){
        // play(int soundID, float leftVolume, float rightVolume, int priority, int loop, float rate)
        soundPool.play(dudaSound, 1.0f, 1.0f, 1, 0, 1.0f);
    }
    public void playStonogaZiobroSound(){
        // play(int soundID, float leftVolume, float rightVolume, int priority, int loop, float rate)
        soundPool.play(ziobroSound, 1.0f, 1.0f, 1, 0, 1.0f);
    }
    public void playStonogaSwetruSound(){
        // play(int soundID, float leftVolume, float rightVolume, int priority, int loop, float rate)
        soundPool.play(swetruSound, 1.0f, 1.0f, 1, 0, 1.0f);
    }
    public void playStonogaKarakanSound(){
        // play(int soundID, float leftVolume, float rightVolume, int priority, int loop, float rate)
        soundPool.play(karakanSound, 1.0f, 1.0f, 1, 0, 1.0f);
    }
}
