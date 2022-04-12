package com.sendiribuat.studentcare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;

import com.cunoraz.gifview.library.GifView;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageButton;
import pl.droidsonroids.gif.GifImageView;

public class SoundMeditationActivity extends AppCompatActivity {

    private CardView playPauseCV;
    private ImageView nextBtn, prevBtn, playPauseImage;
    private GifView gifView;
    private MediaPlayer player;
    private TextView songName, songAuthor;
    int[] listArray = {R.raw.just_chill, R.raw.staringat_nightsky};
    String[] songNameArray = {"Just Chill", "Staring at the Night Sky"};
    String[] songAuthorArray = {"Ahjay Stelino", "Alejandro Magaña (A. M.)"};
    int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sound_meditation);

        songName = findViewById(R.id.songName);
        songAuthor = findViewById(R.id.songAuthor);

        playPauseImage = findViewById(R.id.playPauseImg);
        playPauseImage.setImageResource(R.drawable.pause_icon);

        gifView = findViewById(R.id.animationImage);
        gifView.pause();
        playPauseCV = findViewById(R.id.playPauseCard);
        playPauseCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playPauseImage.setImageResource(R.drawable.play_icon);
                play();
            }
        });
        nextBtn = findViewById(R.id.nextBtn);
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                skipSong();
            }
        });
        prevBtn = findViewById(R.id.previousBtn);
        prevBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                previousSong();
            }
        });
    }

    private void play(){

        if (player == null){
            songName.setText(songNameArray[i]);
            songAuthor.setText(songAuthorArray[i]);
            player = MediaPlayer.create(this,listArray[i]);

            if (player.isPlaying())
            {
                playPauseImage.setImageResource(R.drawable.pause_icon);
                player.pause();
                gifView.pause();
            } else {
                player.start();
                gifView.play();
            }

            player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    nextSong();
                }
            });
        }
        else
        {
            if (player.isPlaying())
            {
                playPauseImage.setImageResource(R.drawable.pause_icon);
                player.pause();
                gifView.pause();
            } else {
                playPauseImage.setImageResource(R.drawable.play_icon);
                player.start();
                gifView.play();
            }
        }
    }

    private void nextSong(){

        if (player !=null){
            player.release();
            player = null;
        }

        if (i >= 1){
            i=0;
            songName.setText(songNameArray[i]);
            songAuthor.setText(songAuthorArray[i]);
            player = MediaPlayer.create(this,listArray[i]);
            player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    nextSong();
                }
            });
        }else{
            i++;
            songName.setText(songNameArray[i]);
            songAuthor.setText(songAuthorArray[i]);
            player = MediaPlayer.create(this,listArray[i]);
            player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    nextSong();
                }
            });
        }
        playPauseImage.setImageResource(R.drawable.play_icon);
        player.start();
    }

    private void skipSong(){

        if (player !=null){
            player.release();
            player = null;
        }

        if (i >= 1){
            i=0;
            songName.setText(songNameArray[i]);
            songAuthor.setText(songAuthorArray[i]);
            player = MediaPlayer.create(this,listArray[i]);
            player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    nextSong();
                }
            });
        }else{
            i++;
            songName.setText(songNameArray[i]);
            songAuthor.setText(songAuthorArray[i]);
            player = MediaPlayer.create(this,listArray[i]);
            player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    nextSong();
                }
            });
        }
        playPauseImage.setImageResource(R.drawable.play_icon);
        player.start();
    }

    private void previousSong(){

        if (player !=null){
            player.release();
            player = null;
        }

        if (i >= 1){
            i=0;
            songName.setText(songNameArray[i]);
            songAuthor.setText(songAuthorArray[i]);
            player = MediaPlayer.create(this,listArray[i]);
            player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    nextSong();
                }
            });
        }else{
            i--;

            if (i <0){
                i = 1;
            }

            songName.setText(songNameArray[i]);
            songAuthor.setText(songAuthorArray[i]);
            player = MediaPlayer.create(this,listArray[i]);
            player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    nextSong();
                }
            });
        }
        playPauseImage.setImageResource(R.drawable.play_icon);
        player.start();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (player !=null){
            player.release();
            player = null;
        }
    }
}