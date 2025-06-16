package com.example.tictictoe;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.LinearInterpolator;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
private MediaPlayer mediaPlayer,mediaPlayer2;
TextView neonEffect;
    private float volumeLevel=0.8f;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        CardView cardView = findViewById(R.id.card);


        ObjectAnimator scaleDown = ObjectAnimator.ofPropertyValuesHolder(
                cardView,
                android.animation.PropertyValuesHolder.ofFloat("scaleX", 0.8f),
                android.animation.PropertyValuesHolder.ofFloat("scaleY", 0.8f));

        scaleDown.setDuration(1000);
        scaleDown.setRepeatCount(ObjectAnimator.INFINITE);
        scaleDown.setRepeatMode(ObjectAnimator.REVERSE);
        scaleDown.setInterpolator(new LinearInterpolator());
        scaleDown.start();

        neonEffect=findViewById(R.id.neonEffect);
        neonEffect.setShadowLayer(30, 0, 0, Color.RED);
        neonEffect.setShadowLayer(50, 0, 0, Color.BLUE);

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer2= MediaPlayer.create(MainActivity.this, R.raw.simple2);
                mediaPlayer2.start();
                Intent intent=new Intent(MainActivity.this, Menu.class);
                startActivity(intent);
            }
        });
    }
    @Override
    protected void onStart() {
        super.onStart();

        mediaPlayer = MediaPlayer.create(this, R.raw.sample_audio);
        mediaPlayer.start();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
    public void increaseVolume() {
        if (volumeLevel < 1.0f) {
            volumeLevel += 0.1f;
            mediaPlayer.setVolume(volumeLevel, volumeLevel);
        }
    }
}